OnlineMarket
============

Client and Server application for online shopping
-------------------------------------------------

##Authours:
+ Frederico de Azevedo Marques - 8936926
+ Lucas Kassouf Crocomo		   - 8937420
+ Roberto Pommella Alegro      - 8936756

* * *

##Downaloading:
'''
git clone https://github.com/OnlineMarketServerWithClient
unzip OnlineMarketServerWithClient.zip
cd OnlineMarketServerWithClient
'''

##Running server:
'''
java -jar OnlineMarketServer.jar
'''

##Running client:
'''
java -jar OnlineMarketClientjar.jar
'''

* * * 

##How to use the program:

##Server application:

You just need to run the server. It's settle to open the 5050 port by default but you may change it inside the code. 
The application will comunicate with anything that follows the **protocol**.

##Client application:

###Start Screen:

When you first launch the application a *login* window pops up. You can enter a user or add a new one using the **Login** or **SingUp** button.
Before click in anything, edit the *server port and adress* fields with valid data. At last, choose between provider, costumer or both to assing your to an group of permissions.
If you chose to singup, a new window will prompt you to enter the new user's information. Fill everything carrefully, then submit.
Having a valided user, just fill the login fields ant press **login**.

In the main window, you have all server's functionalities. You can register a new product, if you are a provider, by clicking the *+* button. A pop up will prompt you to enter the products information. Buy a listed product using the *buy* and inform the amount inside the dialog. Use the *subscription* button to recive an e-mail next time an unavalible product becomes available.

##Protocol

'''
Sign up:
	sent:
		<newuser|username|name|address|telephone|email|id|password|type>
		newuser - command identifier
		username -  username to be created
		name  - name to be created
		address - address then address
		telephone - the telephone number, format XX XXXXXXXXX
		email - the email, format test@test.com
		password - the password
		type:
			customer - the user can only buy products.
			provider - the user can only provide add products
			customerAndProvider - the user can either buy or add products
	received:
		<boolean|message>
		boolean - true if the operation was successful and false otherwise
		message - error message

Login:
	sent: 
		<login|user|password>
		login - command identifier
		user - your username
		password - your password
	received:
		<token>
		token - the session identifier to be used on all other operations

Request:
	sent:
		<request|mode>
		request - command identifier
		mode:
			available - request all available products
			unavailable - request all unavailable products
			all - request all products
	received:
		<id|name|price|bestBefore|provider|status|amount|id|name|...>
		id - the product's id
		name - the product's name
		bestBefore - product is best to consume before this date
		provider - name of the provider for that product
		status - true if the product is available and false if not
		amount - the amount of the same product that is available
		... - repeats for every product matching the request

Subscribe:
	sent:
		<subscribe|token|productId>
		subscribe - command identifier
		token - the session token to identify a user
		productId - the ID of the product the user wants to be notified when is available
	received:
		<boolean>
		boolean - true if the subscription was successful or false if the product is already available

Buy a product:
	sent:
		<buy|token|productId|amount|productId|amount|...>
		buy - command identifier
		token - the session token to identify a user
		productId - the id of the product to be bought
		amount - the amount to be bought
		... - repeats for every product to be bought
	received:
		<boolean|productId|productId|...>
		boolean - true if the operation was successful and false otherwise
		productId - the ID of the products that could not be bought
		... - repeats for every product that was not bought

Add a product:
	sent:
		<addProduct|token|id|name|price|bestBefore|amount|id|name>
		addProduct - command identifier
		token - the session token to identify a user
		id - the id of the product to be added
		name - name of the product
		price - price of the product
		bestBefore - the product is best to consume before this date
		amount - the amount to be added
	received:
		<boolean|message>
		boolean - true if the product was added and false if it was not
		message - error message
'''

* * *


##Important Information

+ JavaMainAPI version: 1.5
+ Java version: 1.8.0_45-b14
+ OpenCSV version: 3.3

#Desing Patterns

+ Singleton
+ Observer

#Project Specifications
You can read this project's specifications [here](https://docs.google.com/document/d/1hZco9xbu2Q6F3rICbgalWUufmuWPvREZUmELk1MDTDs/edit#)








