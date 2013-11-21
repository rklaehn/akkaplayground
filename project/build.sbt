resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.1")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.3.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.9.0")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.0")
