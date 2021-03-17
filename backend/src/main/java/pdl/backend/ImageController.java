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
  public ResponseEntity<?> getImage(@PathVariable("id") long id,  @RequestParam(value = "algorithm", required = false, defaultValue = "") String algorithm) {
    Optional<Image> image = imageDao.retrieve(id); 
    if (!image.isPresent()) {
      return new ResponseEntity<>("Image id=" + id + " not found.", HttpStatus.NOT_FOUND);
    }
    if (!algorithm.equals("")){
      try{
          SCIFIOImgPlus<UnsignedByteType> input = ImageConverter.imageFromJPEGBytes(image.get().getData());
          Color.contrast1(input, 120, 121);

          image.get().setData(ImageConverter.imageToJPEGBytes(input));
      } catch(Exception e){
        return new ResponseEntity<>("Problem in the execution of the function.", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    InputStream inputStream = new ByteArrayInputStream(image.get().getData());
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(inputStream));
  }

  /*
  @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<?> function(@PathVariable("id") long id, @RequestParam(value = "algorithm") String algorithm) {    
    Optional<Image> image = imageDao.retrieve(id); 
    if (!image.isPresent()) {
      return new ResponseEntity<>("Image id=" + id + " not found.", HttpStatus.NOT_FOUND);
    }

    try{
      SCIFIOImgPlus<UnsignedByteType> input = ImageConverter.imageFromJPEGBytes(image.get().getData());
      Color.contrast1(input, 120, 121);
      
      image.get().setData(ImageConverter.imageToJPEGBytes(input));
      
      InputStream inputStream = new ByteArrayInputStream(image.get().getData());
      return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(inputStream));
  
    } catch(Exception e){
      return new ResponseEntity<>("Problem in the execution of the function.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  */
  
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
      imageDao.create(new Image(file.getOriginalFilename(), file.getBytes()));
    } catch (IOException e) {
      return new ResponseEntity<>("Failure to read file", HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>("Image uploaded", HttpStatus.OK);
  }


  @RequestMapping(value = "/images", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getImageList() {
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



  //pour debug
  @RequestMapping(value = "/images/{id}/print", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode print(@PathVariable("id") long id,  @RequestParam String algorithm) throws IOException, FormatException {


    
    ArrayNode nodes = mapper.createArrayNode(); 
    ObjectNode node = mapper.createObjectNode();

    /*
    Optional<Image> image = imageDao.retrieve(id);
    SCIFIOImgPlus<UnsignedByteType> input = ImageConverter.imageFromJPEGBytes(image.get().getData());
    

    final RandomAccess<UnsignedByteType> r = input.randomAccess();
    r.setPosition(0, 0);
    r.setPosition(0, 1);
    r.setPosition(0, 2);
		//node.put("val avant", r.get().get());

    Color.luminosity(input, 100);

    image.get().setData(ImageConverter.imageToJPEGBytes(input));
      
    InputStream inputStream = new ByteArrayInputStream(image.get().getData());

    int b;
    String error = "";
    while ( ( b = inputStream.read() ) != -1 )
      {
        char c = (char)b;         
        error.concat(""+c); //This prints out content that is unreadable.
      }*/

      
    node.put("val", algorithm);
    nodes.add(node);
    return nodes;
  }
}
