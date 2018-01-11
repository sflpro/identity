[![Build status](https://api.travis-ci.org/sflpro/identity.svg?branch=develop)](https://travis-ci.org/sflpro/identity) [![Quality Gate](https://sonarcloud.io/api/badges/gate?key=com.sflpro.identity:identity)](https://sonarcloud.io/dashboard?id=com.sflpro.identity:identity)

# Docker build

To run docker build specify the context.

`docker build . -f docker\Dockerfile`

# Identity

According to the official documentation (https://docs.oracle.com/javase/9/migrate/toc.htm#JSMIG-GUID-954372A5-5954-4075-A1AF-6A9168371246), 
we need to specify some modules manually as a jar command line parameters
 
`--add-modules java.xml.bind` 

## Quote from the link above

### Modules Shared with Java EE Not Resolved by Default
In JDK 9, the modules that contain CORBA or the APIs shared between Java SE and Java EE are not resolved by default when you compile or run code on the class path. These are:

- java.corba — CORBA
- java.transaction — The subset of the Java Transaction API defined by Java SE to support CORBA Object Transaction Services
- java.activation — JavaBeans Activation Framework
- java.xml.bind — Java Architecture for XML Binding (JAXB)
- java.xml.ws — Java API for XML Web Services (JAX-WS), Web Services Metadata for the Java Platform, and SOAP with Attachments for Java (SAAJ)
- java.xml.ws.annotation — The subset of the JSR-250 Common Annotations defined by Java SE to support web services

Existing code with references to classes in these APIs will not compile without changes to the build. Similarly, code on the class path with references to classes in these APIs will fail with NoDefClassFoundError or ClassNotFoundException unless changes are made in how the application is deployed. 

The code for these APIs was not removed in JDK 9, although the modules are deprecated for removal. The policy of not resolving these modules is a first step toward removing these APIs from Java SE and the JDK in a future release.

The migration options for libraries or applications that use these APIs are:

- Use the --add-modules command-line option to ensure that the module with the API is resolved at startup. For example, specify --add-module java.xml.bind to ensure that the java.xml.bind module is resolved. This allows existing code that uses the JAXB API and implementation to work as it did in JDK 8.

  This is a temporary workaround because eventually these modules will be removed from the JDK.

  Using --add-modules java.se.ee or --add-modules ALL-SYSTEM as a workaround is not recommended. These options will resolve all Java EE modules, which is problematic in environments that are also using the standalone versions of APIs.

- Deploy the standalone version of the API (and implementation if needed) on the class path. Each of the Java EE APIs are standalone technologies with projects published in Maven Central.

- Deploy the standalone version of these modules on the upgrade module path. The standalone versions are provided by the Java EE project. 
 
