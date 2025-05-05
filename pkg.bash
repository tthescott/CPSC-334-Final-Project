#!/bin/bash
mkdir temp temp/opt temp/opt/plinko
mkdir temp/DEBIAN
cp target/finalgame-1.0.0.jar temp/opt/plinko
cp DEBIAN/control temp/DEBIAN
chown -R root:root temp/opt 
dpkg-deb --build temp plinko-v1.0.0.deb
rm -rf temp
