./gradlew -x test clean build \
  --refresh-dependencies \
  -Dquarkus.native.enabled=true \
  -Dquarkus.package.jar.enabled=false \
  -Dquarkus.native.container-build=true \
  -Dquarkus.native.container-runtime-options="--platform=linux/amd64"