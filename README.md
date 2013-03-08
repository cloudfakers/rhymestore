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

If you prefer you can also sign up for a Redis account in
[Redis To Go](http://redistogo.com/) and configure the following
properties in the *rhymestore.properties* file:

    rhymestore.redis.host
    rhymestore.redis.port

Compiling Rhymestore
--------------------

Rhymestore can be compiled as a standard [Maven](http://maven.apache.org/) project:

    mvn clean package
  
That will run all unit  tests, and generate the rhymestore.war file
in the *target/* folder.

You will need a running Redis instance while compiling, since unit tests
will test persistence.

To run the integration tests to test Twitter and default rhyme downloading,
you need to configure the Twitter credentials. You will need to export the
following environment variables with the appropriate values:
    
    TWITTER_ACCESSTOKEN
    TWITTER_ACCESSTOKENSECRET
    TWITTER_CONSUMERKEY
    TWITTER_CONSUMERSECRET
    
Once the Twitter credentials have been configured, you can build and run the
integration tests as follows:

    mvn clean verify
    
If you don't have your accessToken and accessTokenSecret, you can use the
`com.rhymestore.twitter.AccessTokenGenerator` main class to generate them.

Deploying
---------

The generated *rhymestore.war* file can be deployed to any servlet container.

In order to connect to Twitter, you will need to configure the same environment
variables:

    TWITTER_ACCESSTOKEN
    TWITTER_ACCESSTOKENSECRET
    TWITTER_CONSUMERKEY
    TWITTER_CONSUMERSECRET

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

Any contribution to the project is welcome. Feel free to check
it out from the [Project site](https://github.com/nacx/rhymestore) and play with it.


Note on patches/pull requests
-----------------------------
 
* Fork the project.
* Create a topic branch for your feature or bug fix.
* Develop in the just created feature/bug branch.
* Add tests for your changes. This is important so I don't break them in a future version unintentionally.
* Commit.
* Send me a pull request. 


Issue Tracking
--------------

If you find any issue, please submit it to the [Bug tracking system](https://github.com/nacx/rhymestore/issues) and we
will do our best to fix it.

License
-------

See LICENSE file.

Contributors
------------

Special thanks to Andrea Cansirro (isochronic) for his generosity by hosting the first
Rhymestore application and for his dedication in setting up an optimal environment to run it.
