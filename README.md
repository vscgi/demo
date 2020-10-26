To set up the project execute the following steps:

1 Run Maven install to generate the jar in the target/ folder.

2 Navigate to the project root folder in your command line interface and run the following commands:

	docker build -f docker/db/Dockerfile -t springboot_database .
	docker run -d -p 5656:3306 --name springboot_database -e MYSQL_ROOT_PASSWORD=supersecret springboot_database

3 Then create the Docker image for the app and run it.

	docker build -f docker/app/Dockerfile -t springboot_app .
	docker run springboot_app

Since the containerized app can currently not connect to the database, you can instead run this in your CLI at the project root folder:

	mvnw spring-boot:run

In order to access the database from the command line type:

	docker exec -it springboot_database bash
	mysql -uroot -p

The password, unless you chose another one up above, is: supersecret











