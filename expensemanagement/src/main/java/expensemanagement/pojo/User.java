package expensemanagement.pojo;

public class User {

	private String name;
	private int userId;
	private String emailId;
	private long phonenumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return userId;
	}

	public void setId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return emailId;
	}

	public void setEmail(String email) {
		this.emailId = email;
	}

	public long getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(long phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", id=" + userId + ", email=" + emailId + ", phonenumber=" + phonenumber + "]";
	}

}
