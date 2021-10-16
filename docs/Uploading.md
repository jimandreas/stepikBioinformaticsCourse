Uploading
========

First use live updating to preview the results.   Run the following in bash at the 
top of the repo:

    mkdocs serve

This will compile the docs and keep them up to date with any live changes.  It will also start 
a local web server.

Aim a web browser at the server:

    http://127.0.0.1:8000/stepikBioinformaticsCourse/

The Script
-----------

The mkdocs provides a handy build and deploy function.  The
trick is that the files are all contained in the <b>"gh-pages"</b>
branch.   But if you simply do the command in Idea or Android Studio,
the tool will want to check all these normally "invisible" files
into Git in the master branch.

The working solution, after looking at OkHttp for example, seems to
be to clone the repo into a temp folder and do all the building there.

With bash in the docs directory, run the `build_and_upload_docs.sh` script.
This clones the repo from github and compiles the docs.   It then uploads the 
docs to the `gh-deploy` utility and initiates the build of the docs there.

Once successfully finished the docs will be available at:  
https://jimandreas.github.io/stepikBioInformaticsCourse/

