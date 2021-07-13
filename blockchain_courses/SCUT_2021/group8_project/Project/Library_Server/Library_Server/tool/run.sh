#!/bin/bash

function usage()
{
    echo " Usage : "
    echo "   bash asset_run.sh deploy"
    exit 0
}

    case $1 in
    deploy)
            [ $# -lt 1 ] && { usage; }
            exit 0
            ;;
    *)
        usage
            ;;
    esac

    java -Djdk.tls.namedGroups="secp256k1" -cp 'apps/*:conf/:lib/*' org.fisco.bcos.client.BackClient $@