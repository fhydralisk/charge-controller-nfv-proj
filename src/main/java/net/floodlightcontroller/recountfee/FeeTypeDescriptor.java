package net.floodlightcontroller.recountfee;

public class FeeTypeDescriptor {
	public String typeDescriptor;
	public String description;
	
	public FeeTypeDescriptor() {
		// TODO Auto-generated constructor stub
		typeDescriptor = "";
		description = "";
	}
	
	public FeeTypeDescriptor(String descriptor, String description) {
		typeDescriptor = descriptor;
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		return (typeDescriptor + description).hashCode();
	}
	
	@Override
	public final boolean equals(Object e) {
		if (e==this)
			return true;
		
		if (e instanceof FeeTypeDescriptor) {
			FeeTypeDescriptor ftd = (FeeTypeDescriptor) e;
			return (ftd.typeDescriptor.equals(typeDescriptor) && ftd.description.equals(description));
		}
		
		return false;
	}

}
