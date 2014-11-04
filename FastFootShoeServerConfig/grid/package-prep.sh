#!/bin/bash
if [[ $UID != 0 ]]; then
    echo "Please run this script with sudo:"
    echo "sudo $0 $*"
    exit 1
fi
echo "Cleaning Up Packages"
yum clean all
mv /var/cache/yum/ /tmp/
mv /var/lib/rpm/__db* /tmp/
rpm --rebuilddb
echo "Shrinking Disk"
/usr/bin/vmware-toolbox-cmd disk shrinkonly

