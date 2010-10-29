THE RHYMESTORE PROJECT
======================
       
The Rhymestore Project provides Twitter integration to
"El Hombre de la rima fácil" and a management interface
to access the rhyme knowledge base. 


Prerequisites
-------------

The Rhymestore project uses Redis to store the rhymes. You
can download it from: http://code.google.com/p/redis/

Refer to Redis installation instructions if you need any
help installing it.


Compiling Rhymestore
--------------------

Rhymestore can be compiled as a standard Maven project:

$ mvn clean package
  
That will generate the rhymestore.war file in the target/
folder.
  

Deploying
---------

Once you have the rhymjestore.war file, you can deploy it to
any servlet container.

Once the application is started, you need to have a running
Redis instance.

If you want to use the example db, just copy the db/redis.rdb
file and rename/move it to the location your Redis will use.
The location and filename of the database file can be configured
in your Redis redis.conf file.

