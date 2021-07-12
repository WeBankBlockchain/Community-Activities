#!/bin/bash

function usage()
{
    echo " Usage : "
    echo "   bash asset_run.sh deploy"
    echo "   bash asset_run.sh getUserMessage mp_openid"
    echo "   bash asset_run.sh getUserLevel mp_openid "
    echo "   bash asset_run.sh getUserCompany mp_openid"
    echo " "
    echo " "
    echo "examples : "
    echo "   bash asset_run.sh deploy"
    echo "   bash asset_run.sh getUserMessage  100003"
    echo "   bash asset_run.sh getUserLevel  100006"
    echo "   bash asset_run.sh getUserCompany  100004"
    exit 0
}

    case $1 in
    deploy)
            [ $# -lt 1 ] && { usage; }
            ;;
    getUserMessage)
            [ $# -lt 2 ] && { usage; }
            ;;
    getUserLevel)
            [ $# -lt 2 ] && { usage; }
            ;;
    getUserCompany)
            [ $# -lt 2 ] && { usage; }
            ;;
    *)
        usage
            ;;
    esac

    java -Djdk.tls.namedGroups="secp256k1" -cp 'apps/*:conf/:lib/*' org.fisco.bcos.trace.server.TraceServer $@