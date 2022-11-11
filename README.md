# travel_app

User:
	uuid: String (Id of user)
	email: String (Email of user)
	name: String (Name of user)
	birthday: Long
	phoneNumber: String
	address: String
	posts: list<String> (List post create by user). List id of post
	friends: list<String> (List friend of user). List uuid of user

ListUserRegister:
	users: list<User>
	
ListOnlineUser:
	users: list<User>

UserPost:
	id: String (Id of post)
	uid: String (Id of User who create post)
	title: String (Title of post)
	description: String 
	createDate: Long
	location: String 
	likes: list<String> (List id of user who liked post)
	comments: list<Comment> (List comment)
	
	
Comment:
	id: String (Id of comment)
	userId: String (Id of user created comment)
	userName: String (Name of user created comment)
	content: String
	createDate: Long
	
