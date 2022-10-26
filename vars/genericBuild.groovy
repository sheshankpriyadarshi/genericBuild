def call(Map config=[:])
{
node {
  stage('SCM') 
    {
        echo 'Gathering code from version control'
        git branch: '${branch}', url: 'https://github.com/sheshankpriyadarshi/jenkins.git'
    }
    stage('Build') 
    {
      input {
        message 'Build ?'
        ok 'do it..'
        parameters
        {
          string(name: 'TARGET_ENVIRONMENT', defaultValue: 'PROD', description: 'Build env....')
        }
      }
      try{
        bat 'dotnet --version' 
        bat 'dotnet build ' + config.target
        echo 'Building new feature...'
        releasenotes(changes:"true")
      }
      catch(e)
      {
        echo 'something is not right'
        echo e.toString();
        currentBuild.result = "FAILURE"
      }
      finally
      {
        //cleanup
      }
    }
      stage('Test') 
    {
        echo 'Testing...'
    }
      stage('Deploy') 
    {
        echo 'Deploying...'
    }
}
}