THE RHYMESTORE PROJECT
======================
       
The Rhymestore Project provides an easy way to create Twitter
bots that respond to user mentions with a sentence that rhymes
perfectly with the received message. It also provides a
management interface to access the rhyme knowledge base. 

Prerequisites
-------------

The Rhymestore project uses Redis to store the rhymes. You
can download it from: [http://redis.io](http://redis.io)

Refer to Redis installation instructions if you need any
help installing it.

Compiling Rhymestore
--------------------

Rhymestore can be compiled as a standard [Maven](http://maven.apache.org/) project:

    mvn clean package
  
That will run all unit  tests, and generate the rhymestore.war file
in the *target/* folder.

You will need a running Redis instance while compiling, since unit tests
will test persistence.

To run the integration tests to test Twitter and default rhyme downloading,
you need to configure the Twitter credentials. You can set the following
profile in your Maven `settings.xml` file:

    <profile>
        <id>rhymestore</id>
        <properties>
            <twitter.consumerKey>your_consumer_key</twitter.consumerKey>
            <twitter.consumerSecret>your_consumer_secret</twitter.consumerSecret>
            <twitter.accessToken>your_access_token</twitter.accessToken>
            <twitter.accessTokenSecret>your_access_token_secret</twitter.accessTokenSecret>
        </properties>
    </profile>
    
Once the Twitter credentials have been configured, you can build and run the
integration tests as follows:

    mvn clean verify -P rhymestore
    
If you don't have your accessToken and accessTokenSecret, you can use the
`com.rhymestore.twitter.AccessTokenGenerator` main class to generate them.

Deploying
---------

The generated *rhymestore.war* file can be deployed to any servlet container.

In order to connect to Twitter, you will need to configure the following system
properties:

    -Dtwitter4j.oauth.consumerKey=your_consumer_key
    -Dtwitter4j.oauth.consumerSecret=your_consumer_secret
    -Dtwitter4j.oauth.accessToken=your_access_token
    -Dtwitter4j.oauth.accessTokenSecret=your_access_token_secret

Once the application is started, you need to have a running Redis instance and
Internet access to let the application connect to the configured Twitter account.

By default, the Rhymestore application comes with HTTP Basic Authentication
security configured. You can configure security in your application server
or disable the security settings in the `web.xml` file.

Customizing and Contributing
----------------------------

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
