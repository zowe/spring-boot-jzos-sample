#!/bin/sh

################################################################################
# This program and the accompanying materials are made available under the terms of the
# Eclipse Public License v2.0 which accompanies this distribution, and is available at
# https://www.eclipse.org/legal/epl-v20.html
#
# SPDX-License-Identifier: EPL-2.0
#
# Copyright IBM Corporation 2019
################################################################################

# Fill in the following variables
ZOWE_ROOT_DIR=    # The root directory of the Zowe installation
ZOWE_EXPLORER_HOST=   # Host name of the Zowe installation
JZOS_PORT=57949       # The address where your application is running 

echo "Configuring jzos behind API mediation layer" 

# Create the static api definitions folder
STATIC_DEF_CONFIG=$ZOWE_ROOT_DIR"/api-mediation/api-defs"
TEMP_DIR=./

# Add static definition for JzOS
cat <<EOF >$TEMP_DIR/jzos.yml
#
services:
  - serviceId: jzos
    title: IBM z/OS JzOS
    description: IBM z/OS JzOS REST API service
    catalogUiTileId: jzos
    instanceBaseUrls:
      - https://$ZOWE_EXPLORER_HOST:$JZOS_PORT/
    homePageRelativeUrl:
    routedServices:
      - gatewayUrl: api/v1
        serviceRelativeUrl: api/v1/jzos
    apiInfo:
      - apiId: com.zowe.jzos
        gatewayUrl: api/v1
        version: 1.0.0
        documentationUrl: https://$ZOWE_EXPLORER_HOST:$JZOS_PORT/api/v1/swagger-ui.html
catalogUiTiles:
  jzos:
    title: z/OS JzOS services
    description: IBM z/OS JzOS REST services
EOF
iconv -f IBM-1047 -t IBM-850 $TEMP_DIR/jzos.yml > $STATIC_DEF_CONFIG/jzos.yml	

chmod -R 777 $STATIC_DEF_CONFIG/jzos.yml

echo "Completed jzos" 
