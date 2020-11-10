# ServerBlog
Project repository used to develope the examples used by [this blog series](https://dev.to/funcke/series/9632) on serverside Java.

The project splits into two parts:
* server side code: everything in `scr/main`
* client application: `AdministrationApp`

The server consists of an embedded Jetty instance and a Jersey API utilizing Hibernate and SQLite3 for storage.
