# csis501programmingassignment-grossmeyer-weller

This program runs a DHT on up to four peers in a circular P2P network. The key for the DHT is a movie title and the value is the IP address that it is stored at (user entered).

To execute, run the classes in this order: server3.java, server2.java, server1.java, server0.java, client.java. This sets up the initial state with the client connected to server0.java and all four of the peers connected in a circle.

Part A:

-To insert a key,value pair into the DHT, the user selects option 1 on the client menu.
 The user then enters the movie name as the key followed by the IP address where it is stored as the value.
 The users choice, key, and value are then passed on to server0 which generates the hash key and compares it with it's identifier.
 If the hash key is greater than the identifier, the choice, key, and value are passed onto the next peer in the circle.
 This process continues until the hash key is less than or equal to a peers identifier. 
 The key,value pair is then stored in that peers hash table.
 A printout of that hash table is then sent back to the client to confirm that the pair was added.
 
-To get a value from the DHT, the user selects option 2 on the client menu.
 The user then enters a movie name as the key to search the DHT for a key,value pair with that key.
 The users choice and key are then passed on to server0 which generates the hash key and compares it with it's identifier.
 If the hash key is greater than the identifier and it is not in the hash table currently, 
 the choice and key are passed onto the next peer in the circle.
 If the key is found in one of the hash tables, the value is then sent back through the circle to the client, 
 otherwise a key not found message is sent back.
 
Part B:

-The P2P network can maintain it's structure if a peer disconnects from the network.
 If one of the peers disconnects, the next peer in the circle immediately catches a SocketException.
 That peer then restarts it's incoming connection and starts listening again.
 When the client tries an action that requires the previous peer to try to contact the disconnected peer a SocketException is caught.
 The previous peer then reconnects to the next peer by incrementing it's port and attempting a connection.
 If it connects, the previous peer sends a network interruption message to the client and instructs it to try again.
 The next peer then takes over the keys handled by the disconnected peer and the network has maintained its structure.
 The client can then continue putting and getting from the DHT as if nothing happened.
 
-
