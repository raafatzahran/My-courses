### Git instructions
-------------------
Go to github.
Log in to your account.
Click the new repository button in the top-right. You’ll have an option there to initialize the repository with a README file, but I don’t.
Click the “Create repository” button.
-------------------
Create directory in local machine
cd directory
git init
--> create .gitignore
git remote add origin git@github.com:username/new_repo
git push -u origin master
-------------------
git pull origin master
git branch
git checkout -b branch1
--> Do changes in the code
git add .
git commit -m "pr1 from branch1"
git push -u origin branch1
git checkout master
--> Do changes in the code
git branch
git pull origin master
-------------------
