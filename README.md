Seda programmi saab kompileerida ainult linux masinal.
Kui kasutad Windows masinat siis, peab alla laadima WSL-i.

Kompileerimise juhendid:

sudo apt update && sudo apt upgrade -y
sudo curl -s "https://get.sdkman.io" | bash
sdk install java 17.0.4-
wget https://github.com/gluonhq/graal/releases/download/gluon-22.1.0.1-Final/graalvm-svm-java17-linux-gluon-22.1.0.1-Final.tar.gz
