Seda programmi saab kompileerida ainult linux masinal.
Kui kasutad Windows masinat siis, peab alla laadima WSL-i.

## Kompileerimise juhendid:
Navigeeri sellesse kausta WSL-is, kus on sul alla laetud see projekt. <br>
Seejärel sisesta see käsk <br>
sudo apt update && sudo apt upgrade -y;
sudo curl -s "https://get.sdkman.io" | bash;
sdk install java 17.0.8-tem;
sdk default java 17.0.8-tem;
wget ~/ https://github.com/gluonhq/graal/releases/download/gluon-22.1.0.1-Final/graalvm-svm-java17-linux-gluon-22.1.0.1-Final.tar.gz;
tar -zxf ~/graalvm-svm-java17-linux-gluon-22.1.0.1-Final.tar.gz;
export GRAALVM_HOME=$HOME/graalvm-svm-java17-linux-gluon-22.1.0.1-Final;
mvn gluonfx:compile gluonfx:link gluonfx:package gluonfx:install gluonfx:nativerun;
<br>Selle käsu täitmine võib võtta mitu minutit<br>
