package quiz_builder.shared;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Image {
	
	private Blob imageBlob;
	
	private Entity entity;
	
	public Image(Entity entity){
		this.entity = entity;
		this.imageBlob = (Blob) entity.getProperty("imageBlob");
	}
	
	public Image(String strKey) throws EntityNotFoundException{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.stringToKey(strKey);
		this.entity = ds.get(key);
		this.imageBlob = (Blob) entity.getProperty("imageBlob");
	}
	
	public Image(byte[] bytes){
		this.imageBlob = new Blob(bytes);
		this.entity = new Entity("image");
		this.entity.setProperty("imageBlob", this.imageBlob);
	}
	
	public Entity toEntity(){
		return this.entity;
	}
	
	public byte[] getBytes(){
		return this.imageBlob.getBytes();
	}
	
	public void saveToDS(){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		ds.put(this.toEntity());
	}
	
	public String getStringKey(){
		return KeyFactory.keyToString(this.entity.getKey());
	}
	
	public void deleteFromDS(){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		ds.delete(this.entity.getKey());	
	}

}
