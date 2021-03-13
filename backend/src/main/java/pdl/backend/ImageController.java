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



import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
// import io.scif.img.ImgIOException;
// import io.scif.img.ImgOpener;
// import io.scif.img.ImgSaver;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.exception.IncompatibleTypeException;
import java.io.File;
import net.imglib2.view.Views;
import net.imglib2.view.IntervalView;
import java.util.Arrays;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.img.array.ArrayImgs;

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
  public ResponseEntity<?> getImage(@PathVariable("id") long id) {

    Optional<Image> image = imageDao.retrieve(id);    

    if (image.isPresent()) {
        InputStream inputStream = new ByteArrayInputStream(image.get().getData());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
            .body(new InputStreamResource(inputStream));
    }
    return new ResponseEntity<>("Image id=" + id + " not found.", HttpStatus.NOT_FOUND);
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
    if (!contentType.equals(MediaType.IMAGE_JPEG.toString())) {
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

  @RequestMapping(value = "images/{id}?algorithm=contrast", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<?> contrast(@PathVariable("id") long id) {
    Optional<Image> image = imageDao.retrieve(id); 

    System.out.println("salut");

    if (!image.isPresent()) {
      return new ResponseEntity<>("Image id=" + id + " not found.", HttpStatus.NOT_FOUND);
    }
    InputStream inputStream = new ByteArrayInputStream(image.get().getData());

    testDefaultASCII(image.get().getData());
    
    //récupérer à partir de inputStream le input de RGB.java
    /*
    
    input;
    RGB.contrast(input)
    InputStream inputOut
    imageDao.create(new Image(file.getOriginalFilename(), file.getBytes()));
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(inputOut));
    
    */
    return new ResponseEntity<>("test", HttpStatus.OK);
  }

  public void testDefaultASCII(byte[] data) {
    // character set used in DefaultASCII, could be updated if necessary
    // final String CHARS = "#O*o+-,. ";
    // final int len = CHARS.length();
    // final int width = 10;
    // final int offset = 47;
    // final byte[] array = new byte[width * len];
    // for (int i = 0; i < len; i++) {
    //   for (int j = 0; j < width; j++) {
    //     array[i * width + j] = (byte) (offset + i * width + j);
    //   }
    // }
    System.out.println(data);

    //final Img<UnsignedByteType> img = ArrayImgs.unsignedBytes(data, width, len);
    final Img<UnsignedByteType> img = ArrayImgs.unsignedBytes(data, data.length);

    System.out.println(img);

    final RandomAccess<UnsignedByteType> r = img.randomAccess();

    // final String ascii = (String) ops.run(DefaultASCII.class, img);
    // for (int i = 0; i < len; i++) {
    //   for (int j = 0; j < width; j++) {
    //     assertTrue(ascii.charAt(i * (width + 1) + j) == CHARS.charAt(i));
    //   }
    //   assertTrue(ascii.charAt(i * (width + 1) + width) == '\n');
    // }
  }

}
