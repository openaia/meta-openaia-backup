# meta-openaia

![Build](https://github.com/edgeble/meta-openaia/actions/workflows/neu2a-v2-kirkstone.yml/badge.svg)
[![Github All Releases](https://img.shields.io/github/downloads/atom/atom/total.svg?style=flat)](https://github.com/edgeble/meta-openaia/releases)

Yocto/OE BSP layer for the Edgeble [OpenAIA](https://www.openaia.io) - Based on upstream [meta-rockchip](https://git.yoctoproject.org/meta-rockchip)

## Build Host
To install the required packages on a Debian based distribution (Ubuntu etc) run

```
sudo apt install gawk wget git diffstat unzip texinfo gcc build-essential \
chrpath socat cpio python3 python3-pip python3-pexpect xz-utils \
debianutils iputils-ping python3-git python3-jinja2 libegl1-mesa \
libsdl1.2-dev pylint xterm python3-subunit mesa-common-dev zstd liblz4-tool
```

## Configure Yocto/OE

In order to build an image with BSP support for a given release, you need to download the corresponding layers described in the "Dependencies" section.

```shell
~ $ mkdir yocto; cd yocto
~/yocto $ git clone git://git.openembedded.org/bitbake -b master
~/yocto $ git clone git://git.openembedded.org/openembedded-core -b kirkstone
~/yocto $ git clone git://git.yoctoproject.org/meta-arm -b kirkstone
~/yocto $ git clone git://git.openembedded.org/meta-openembedded -b kirkstone
~/yocto $ git clone git@github.com:edgeble/meta-openaia.git -b kirkstone
```

And put the meta-openaia layer here too.

Then you need to source the configuration script:

```shell
~/yocto $ source openembedded-core/oe-init-build-env
```

Having done that, you can build a image for a edgeble boards by adding the location of the meta-openaia layer to bblayers.conf, along with any other layers needed.

For example:

```makefile
# conf/bblayers.conf
BBLAYERS ?= " \
  ${TOPDIR}/../openembedded-core/meta\
  ${TOPDIR}/../meta-arm/meta-arm \
  ${TOPDIR}/../meta-arm/meta-arm-toolchain \
  ${TOPDIR}/../meta-openaia \
  ${TOPDIR}/../meta-openembedded/meta-oe \
  ${TOPDIR}/../meta-openembedded/meta-python \
  ${TOPDIR}/../meta-openembedded/meta-networking \
  "
```

To enable a particular machine, you need to add a MACHINE line naming the BSP to the local.conf file:

```makefile
  MACHINE = "neu6b"
```

Enable systemd in your Yocto configuration by adding the following to your local.conf file

```makefile
INIT_MANAGER = "systemd"
```

Enable disto features needed to support the pacakges by adding the following to your local.conf file

```makefile
DISTRO_FEATURES:append = " bluetooth wifi"
```

## Building Yocto/OE

You should then be able to build a image as such:

```shell
bitbake core-image-full-cmdline
```

At the end of a successful build, you should have an .wic image in /path/to/yocto/build/tmp-glibc/deploy/\<MACHINE\>/

If you want to boot the image on microSD card the follow below steps.

```shell
cd tmp-glibc/deploy/images/\<MACHINE\>
sudo bmaptool copy --bmap core-image-full-cmdline-neu6b.wic.bmap core-image-full-cmdline-neu6b.wic.xz /dev/sdX
```
