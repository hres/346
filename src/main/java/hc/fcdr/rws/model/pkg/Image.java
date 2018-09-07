package hc.fcdr.rws.model.pkg;

public class Image {
	
	private String image_name;
	private Integer image_id;
	
	
	public Image() {
		super();
		this.image_name = null;
		this.image_id = null;
	}
	
	public Image(String image_name, Integer image_id) {
		super();
		this.image_name = image_name;
		this.image_id = image_id;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public Integer getImage_id() {
		return image_id;
	}

	public void setImage_id(Integer image_id) {
		this.image_id = image_id;
	}
	
	
	

}
