pipeline{
	agent { label 'master'}
		stages{
		
			stage ('test'){
				steps{
					//dir("build_java"){
						Sh 'gradle test'
					//}
				}
			}

			stage ('Build application'){
				steps{
						echo "mvn clean install -Dmaven.test.skip=true"
				}
			}
			
			stage ('Create docker image'){
				steps{
						echo "creando docker"
				}
			}
		}
}