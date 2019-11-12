docker images | grep ruanxingke | awk '{ print $1}' | while read image
do
	docker image rm -f ${image}
done

WORK_PATH=`pwd`
for module in zuul auth account product order
do
	echo "start to build ---------->"${module}
	cd ${WORK_PATH}/${module}
	sh build.sh
done
