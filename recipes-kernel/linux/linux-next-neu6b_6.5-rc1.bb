SECTION = "kernel"
DESCRIPTION = "Linux-6.5-rc1 Neu6B  Linux Next"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

require recipes-kernel/linux/linux-yocto.inc

inherit kernel

LINUX_BRANCH ?= "linux-6.5-rc1"

SRC_URI = " \
	git://git@github.com/edgeble/linux-next.git;protocol=ssh;branch=${LINUX_BRANCH} \
"

SRCREV = "a3e15ab8e64aff2b742b5fcc4960d1610870fcc0"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

DEPENDS += "openssl-native util-linux-native"