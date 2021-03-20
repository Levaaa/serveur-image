package pdl.backend;

public class Image {
  private static Long count = Long.valueOf(0);
  private Long id;
  private String name;
  private byte[] data;
  private String type;

  public Image(final String name, final byte[] data, String type) {
    id = count++;
    this.name = name;
    this.data = data;
    this.type = type;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public String getType() {
    return type;
  }
}
