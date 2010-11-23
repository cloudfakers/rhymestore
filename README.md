THE RHYMESTORE PROJECT
======================
       
The Rhymestore Project provides an easy way to create Twitter
bots that respond to user mentions with a sentence that rhymes
perfectly with the received message. It also provides a
management interface to access the rhyme knowledge base. 

Prerequisites
-------------

The Rhymestore project uses Redis to store the rhymes. You
can download it from: [http://code.google.com/p/redis/](http://code.google.com/p/redis/)

Refer to Redis installation instructions if you need any
help installing it.

Compiling Rhymestore
--------------------

Rhymestore can be compiled as a standard [Maven](http://maven.apache.org/) project:

    mvn clean verify
  
That will run all unit and integration tests, and generate the rhymestore.war file
in the *target/* folder.

Deploying
---------

The generated *rhymestore.war* file can be deployed to any servlet container.

Once the application is started, you need to have a running
Redis instance and Internet access to let the application connect to the configured
Twitter account.

Customizing and Contributing
----------------------------

You can change the Twitter user by editing the `twitter4j.properties` file. To
generate the required OAuth tokens, you can run the `com.rhymestore.twitter.AccessTokenGenerator`
utility class and put the generated values in that file.

Currently, there is only support for the Spanish language. Support for rhymes in other
languages can be added by implementing the `com.rhymestore.lang.WordParser` interface
and adding a unit test class that extends the base class: `com.rhymestore.lang.AbstractWordParserTest`.
The WordParser implementation to use can be configured in the `rhymestore.properties` file.

Any contribution to the project is welcome. Feel free to check
it out from the [Project site](https://github.com/nacx/rhymestore) and play with it.

Issue Tracking
--------------

If you find any issue, please submit it to the [Bug tracking system](https://github.com/nacx/rhymestore/issues) and we
will do our best to fix it.

License
-------

See LICENSE file.
