SRC_URI = " \
    git://github.com/nxp-imx/nnshark.git;protocol=https;branch=2021.10.imx;name=nnshark \
    git://github.com/GStreamer/common.git;protocol=https;name=common;destsuffix=git/common;nobranch=1 \
"

# Definiamo esplicitamente le SRCREV
SRCREV_nnshark = "${AUTOREV}"
SRCREV_common = "${AUTOREV}"
SRCREV_FORMAT = "nnshark_common"
