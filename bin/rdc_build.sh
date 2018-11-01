#/bin/bash

mvn clean package -U -Dmaven.test.skip=true

cd $BUILD_WORK_PATH/deploy

if [ "$ENV_TYPE" == "test" ]; then

	echo "buiding with test env"	
	mvn assembly:assembly -Pdeploy -Dconfig=test  -Dmaven.test.skip=true		

elif [ "$ENV_TYPE" == "dev" ]; then

	echo "buiding with show env"	
	mvn assembly:assembly -Pdeploy -Dconfig=dev  -Dmaven.test.skip=true

else

	echo "buiding with online env"	
	mvn assembly:assembly -Pdeploy -Dconfig=online  -Dmaven.test.skip=true

fi

echo ".......build done......"