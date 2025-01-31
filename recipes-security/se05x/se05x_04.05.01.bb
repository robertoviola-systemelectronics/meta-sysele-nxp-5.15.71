DESCRIPTION = "NXP Plug and Trust Middleware"
LICENSE = "CLOSED & MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

NXP_PLUG_TRUST_MW = "${PN}_mw_v${PV}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

inherit dos2unix cmake setuptools3

DEPENDS += "openssl"
RDEPENDS:${PN} += "\
    bash \
    python3-cffi \
    python3-cryptography \
    python3-click \
    func-timeout \
    "

# Allow symlink .so in non-dev package
INSANE_SKIP:${PN} += "dev-so"

S = "${WORKDIR}/${NXP_PLUG_TRUST_MW}/simw-top"
B = "${WORKDIR}/${NXP_PLUG_TRUST_MW}/build"

SRC_URI = "file://${NXP_PLUG_TRUST_MW}.zip \
           file://0001-fix-openssl-3-compatibility.patch;patchdir=.. \
           file://0002-fix-core-json-as-static-library.patch;patchdir=.. \
           file://0003-fix-remove-use-of-missing-python-cryptography-api.patch;patchdir=.. \
           file://0004-fix-pycli-setup.py.patch;patchdir=.. \
           "

EXTRA_OECMAKE:append = "\
    -DCMAKE_BUILD_TYPE=Release \
    -DWithSharedLIB=ON \
    -DPTMW_Host=iMXLinux \
    -DPTMW_HostCrypto=OPENSSL \
    -DPTMW_SMCOM=T1oI2C \
    -DPTMW_Applet=SE05X_C \
    -DPTMW_SE05X_Auth=None \
    -DPTMW_SE05X_Ver=03_XX \
    -DPAHO_BUILD_DEB_PACKAGE=OFF \
    -DPAHO_BUILD_DOCUMENTATION=OFF \
    -DPAHO_BUILD_SHARED=ON \
    -DPAHO_BUILD_STATIC=OFF \
    -DPAHO_BUILD_SAMPLES=OFF \
    -DPAHO_ENABLE_CPACK=OFF \
    -DPAHO_ENABLE_TESTING=OFF \
    -DPAHO_WITH_SSL=ON \
    "

SETUPTOOLS_SETUP_PATH = "${S}/pycli/src"

FILES:${PN} += "${datadir}/se05x \
                ${bindir} \
                ${libdir}/*.so \
                ${libdir}/*.so.* \
                "
FILES:${PN}-dev = "${includedir} ${libdir}/cmake"

do_configure() {
    cmake_do_configure
    setuptools3_do_configure
}

do_compile () {
    cmake_do_compile
    setuptools3_do_compile
}

do_install () {
    cmake_do_install
    setuptools3_do_install
}
