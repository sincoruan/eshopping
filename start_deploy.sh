WORK_PATH=`pwd`
echo ${WORK_PATH}
#exit
for module in env zuul auth account product order
do
	echo "start to deploy---------->"${module}
	cd ${WORK_PATH}/${module}
	sh deploy.sh
done

