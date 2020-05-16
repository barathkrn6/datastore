# datastore

# Assusming below things have already installed on the given laptop

	1. Docker
	2. Maven
	3. Java 8
  	4. VirtualBox/Hazelcast

# VirtualBox/Hazelcast Setup

  1. Install VirtualBox (https://www.virtualbox.org/wiki/Downloads)
  2. Install CentOS 7 image in VirtualBox (https://www.osboxes.org/centos/)
  3. Create 3 servers from CentOs 7 image.
  4. Make a static IP setup on 3 servers.
  5. Download Hazelcast to any location on all the servers
  6. Update hazelcast.xml as below.
  
            <multicast enabled="false">
                <multicast-group>224.2.2.3</multicast-group>
                <multicast-port>54327</multicast-port>
            </multicast>
            <tcp-ip enabled="true">
                <interface>{parent_ip}</interface>
                <member-list>
                    <member>{other_node_ip}</member>
                    <member>{other_node_ip}</member>
                </member-list>
            </tcp-ip>
            
    7. Now start all the 3 Hazelcast server (nohup ./{hazelcast_location}/bin/start.sh &)
  

# Application setup

Clone the spring boot application and run the below commands (Run these commands from spring boot application root directory)

Step 1 : Build the application to generate the jar file
	
	mvn clean install

Step 2 : Create a docker Image for spring boot application
	
	docker build . -t datastore

Step 3 : Run the spring boot application by linked database docker
	
	docker run -p 8080:8080 --name datastore -d datastore

# Swagger URL

	http://{host_name/ip_addr}:8080/swagger-ui.html

# Health Check URL

	http://{host_name/ip_addr}:8080/health_check
