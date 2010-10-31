THE RHYMESTORE PROJECT
======================
       
The Rhymestore Project provides an easy way to create Twitter
bots that respond to user mentions with a sentence that rhymes
perfectly with the received message. It also provides a
management interface to access the rhyme knowledge base. 


Prerequisites
-------------

The Rhymestore project uses Redis to store the rhymes. You
can download it from: http://code.google.com/p/redis/

Refer to Redis installation instructions if you need any
help installing it.


Compiling Rhymestore
--------------------

Rhymestore can be compiled as a standard Maven project:

    mvn clean package
  
That will generate the rhymestore.war file in the target/
folder.
  

Deploying
---------

Once you have the rhymestore.war file, you can deploy it to
any servlet container.

Once the application is started, you need to have a running
Redis instance.


Contributing
------------

Any contribution to the project is welcome. Feel free to check
it out and play with it.

Currently, there is only support for the Spanish language.
Support for rhymes in other languages can be added by implementing
the `com.rhymestore.lang.WordParser` interface.

