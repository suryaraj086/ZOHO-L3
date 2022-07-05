package zkart;

public class UserDetails {

	String name;
	String password;
	String email;
	long mobileNumber;
	int credit;
	boolean isAdmin;

	public UserDetails(String name, String pass, String email, long mobileNum, int credit, boolean admin) {
		this.name = name;
		password = pass;
		this.email = email;
		mobileNumber = mobileNum;
		this.credit = credit;
		isAdmin = admin;
	}

	public boolean passwordValidator(String pass) throws Exception {
		if (!password.equals(pass)) {
			throw new Exception("Invalid password");
		}
		return isAdmin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getCredit() {
		return credit;
	}

	@Override
	public String toString() {
		return "UserDetails [name=" + name + ", password=" + password + ", email=" + email + ", mobileNumber="
				+ mobileNumber + ", credit=" + credit + "]";
	}

	public void setCredit(int credit) {
		this.credit += credit;
	}

}
