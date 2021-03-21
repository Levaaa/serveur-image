package pdl.backend;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.scif.FormatException;
// a faire le tri
import io.scif.img.SCIFIOImgPlus;

import net.imglib2.RandomAccess;
import net.imglib2.type.numeric.integer.UnsignedByteType;


@RestController
public class ImageController {
  
  @Autowired
  private ObjectMapper mapper;
  
  private final ImageDao imageDao;
  
  @Autowired
  public ImageController(ImageDao imageDao) {
    this.imageDao = imageDao;
  }
  
  @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<?> getImage(@PathVariable("id") long id,  @RequestParam(value = "algorithm", required = false, defaultValue = "")//...
   String algorithm, @RequestParam(value = "p1", required = false, defaultValue = "problem") String p1) {
    Optional<Image> image = imageDao.retrieve(id); 
    if (!image.isPresent()) {
      return new ResponseEntity<>("Image id=" + id + " not found.", HttpStatus.NOT_FOUND);
    }
    InputStream inputStream;
    Optional<Image> modifiedImage = imageDao.retrieve(id);//parce qu'on est obligé de l'initialiser
    
    if (!algorithm.equals("")){
      Boolean found = false;
      List<Image> listImage = imageDao.retrieveAll();
      
      for (Image img : listImage) {     //verifier si l'image existe deja pour la modifier et eviter d'en creer plusieurs
        if(img.getName().equals(algorithm + "_" + image.get().getName())){
          long modifiedId = img.getId();
          modifiedImage = imageDao.retrieve(modifiedId);
          modifiedImage.get().setData(image.get().getData());
          found = true;
          break;
        }
      }
      if(!found){
        long modifiedId = imageDao.create(new Image(algorithm + "_" + image.get().getName(), image.get().getData(), image.get().getType()));
        modifiedImage = imageDao.retrieve(modifiedId);
      }
      try{
          SCIFIOImgPlus<UnsignedByteType> input = ImageConverter.imageFromJPEGBytes(modifiedImage.get().getData());
        try{
          int param;
          switch(algorithm){
            case "contrast":
              if(!p1.equals("problem"))
                return new ResponseEntity<>("Unwanted parameter.", HttpStatus.BAD_REQUEST);
              Color.contrast2(input, 0, 255);
              break;
            case "brightness":
              param = Integer.parseInt(p1);
              if(param < 0 && param > 255)
                return new ResponseEntity<>("Unvalid parameter.", HttpStatus.BAD_REQUEST);
              
              Color.brightness(input, param);
              break;
            case "equalizer":
              if(!p1.equals("problem"))
                return new ResponseEntity<>("Unwanted parameter.", HttpStatus.BAD_REQUEST);
              Color.equalizer(input);
              break;
            case "toGrey":
              if(!p1.equals("problem"))
                return new ResponseEntity<>("Unwanted parameter.", HttpStatus.BAD_REQUEST);
              Color.rgbToGrey(input);
              break;
            case "coloration":
              param = Integer.parseInt(p1);
              if(param < 0 && param > 360)
                return new ResponseEntity<>("Unvalid parameter.", HttpStatus.BAD_REQUEST);

              Color.coloration(input, param);
              break;
            default :
              return new ResponseEntity<>("Unknown algorithm.", HttpStatus.BAD_REQUEST);
          }
        }catch(Exception e){
          return new ResponseEntity<>("Integer expected.", HttpStatus.BAD_REQUEST);
        }
          modifiedImage.get().setData(ImageConverter.imageToJPEGBytes(input));
      } catch(Exception e){
        return new ResponseEntity<>("Problem in the execution of the function.", HttpStatus.INTERNAL_SERVER_ERROR);
      }
      inputStream = new ByteArrayInputStream(modifiedImage.get().getData());
    } else {
      inputStream = new ByteArrayInputStream(image.get().getData());
    }
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(inputStream));
  }
  
  @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteImage(@PathVariable("id") long id) {

    Optional<Image> image = imageDao.retrieve(id);

    if (image.isPresent()) {
      imageDao.delete(image.get());
      return new ResponseEntity<>("Image id=" + id + " deleted.", HttpStatus.OK);
    }
    return new ResponseEntity<>("Image id=" + id + " not found." , HttpStatus.NOT_FOUND);
  }

  @RequestMapping(value = "/images", method = RequestMethod.POST)
  public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes) {

    if(file.isEmpty()){
      return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
    }
    
    String contentType = file.getContentType();
    if (!contentType.equals(MediaType.IMAGE_JPEG.toString()) && !contentType.equals(MediaType.valueOf("image/tiff").toString())) {
      return new ResponseEntity<>("Only JPEG file format supported", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    try {
      imageDao.create(new Image(file.getOriginalFilename(), file.getBytes(), contentType));
    } catch (IOException e) {
      return new ResponseEntity<>("Failure to read file", HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>("Image uploaded", HttpStatus.OK);
  }


  @RequestMapping(value = "/images", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getImageList(){
    ArrayNode nodes = mapper.createArrayNode();
    List<Image> imgList = imageDao.retrieveAll();
    
    for (Image image : imgList) {
      ObjectNode node = mapper.createObjectNode();
      SCIFIOImgPlus<UnsignedByteType> input;
      try{
        input = ImageConverter.imageFromJPEGBytes(image.getData());
      } catch (Exception e) {
        return null;
      }
      final int iw = (int) input.max(0) + 1;
		  final int ih = (int) input.max(1) + 1;
		  final int ic = (int) input.max(2) + 1;
      String size = iw + "*" + ih + "*" + ic;
      
      node.put("name", image.getName());
      node.put("id", image.getId());  
      node.put("type", image.getType());
      node.put("size", size);

      nodes.add(node);
    }
    return nodes;
  }

  @RequestMapping(value = "/images/get", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getAllImages(){
    ArrayNode nodes = mapper.createArrayNode();
    List<Image> imgList = imageDao.retrieveAll();
    
    for (Image image : imgList) {
      ObjectNode node = mapper.createObjectNode();
      
      node.put("name", image.getName());
      node.put("id", image.getId());

      nodes.add(node);
    }
    return nodes;
  }
}
