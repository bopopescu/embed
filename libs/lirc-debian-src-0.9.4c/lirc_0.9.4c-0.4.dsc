Format: 3.0 (quilt)
Source: lirc
Binary: lirc, lirc-doc, liblirc0, liblircclient0, liblircclient-dev, liblirc-dev, liblirc-client0, lirc-x
Architecture: any all
Version: 0.9.4c-0.4
Maintainer: lirc Maintainer Team <pkg-lirc-maint@lists.alioth.debian.org>
Uploaders:  Stefan Lippers-Hollmann <s.l-h@gmx.de>, Alec leamas <leamas.alec@gmail.com>
Homepage: http://sf.net/p/lirc
Standards-Version: 3.9.8
Vcs-Browser: https://sourceforge.net/p/lirc/git/ci/debian/tree/
Vcs-Git: git://git.code.sf.net/p/lirc/git -b debian
Build-Depends: debhelper (>= 9), dh-autoreconf, dh-systemd, dh-python, doxygen, module-init-tools | kmod [linux-any], libasound2-dev [linux-any kfreebsd-any], libftdi1-dev | libftdi-dev, libsystemd-daemon-dev | libsystemd-dev, libudev-dev, libusb-1.0-0-dev [linux-any], libusb-dev, libx11-dev, man2html-base, pkg-config, portaudio19-dev | libportaudio19-dev, python3, python3-yaml, systemd | systemd-services, xsltproc
Package-List:
 liblirc-client0 deb libs extra arch=any
 liblirc-dev deb libdevel extra arch=any
 liblirc0 deb libs extra arch=any
 liblircclient-dev deb libdevel extra arch=any
 liblircclient0 deb libs extra arch=any
 lirc deb utils extra arch=any
 lirc-doc deb doc extra arch=all
 lirc-x deb utils extra arch=any
Checksums-Sha1:
 86cd7e8b6536a4d1078144b3bb1a894bb006e8e5 2499425 lirc_0.9.4c.orig.tar.gz
 623aaccfb89b8646c31af7cd9d3e952df5744f1d 36368 lirc_0.9.4c-0.4.debian.tar.xz
Checksums-Sha256:
 00af1adca9f30ba55928f39520e28dd533892e548082ddc7a9e5e5019355a430 2499425 lirc_0.9.4c.orig.tar.gz
 d1001799d18c38653a4d53f2d1ea3038b2cb681fe71ee5d68f7dc26a82298a22 36368 lirc_0.9.4c-0.4.debian.tar.xz
Files:
 cb9ee752aa3a67a689e55aea7fdb6bae 2499425 lirc_0.9.4c.orig.tar.gz
 66389bfe79ef714e002b9ae5ce166574 36368 lirc_0.9.4c-0.4.debian.tar.xz
