ABS Tools
=========

[![CircleCI](https://img.shields.io/circleci/project/abstools/abstools.svg)](https://circleci.com/gh/abstools/abstools)
[![GitHub release](https://img.shields.io/github/release/abstools/abstools.svg)](https://github.com/abstools/abstools/releases/latest)
[![Gitter](https://badges.gitter.im/abstools/general.svg)](https://gitter.im/abstools/general?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

University of Nottingham Malaysia
---------------------------------
Undergraduate Individual Disertation  
------------------------------------
**Name: Gwee Kent Yuan**  
**Student ID: 20103627**  
**OWA: hcykg1**   

This is an final year project for my undergraduate certification. This project is an extended version of the original ABS compiler/tool suite with implementation of other feature model analysis operation added. The original ABS compiler/tool suite can be found at https://github.com/abstools/abstools.

**Warning**  
** Please run the project with a Linux/Unix OS if possible. Example, Any Linux or Mac OS  
** You may run into compilation issue when running with Window OS

Software Requirement
-------------------
* Java version 11 or greater.
* Erlang version 23 or greater.
* Gradle 6.8 (Optional - it is use to compile the project locally)

Step to use
------------
1. Obtain the project file and 
2. Using a console go to the program directory and compile the ABS compiler using ` gradle assemble` in your console. 
  Or you can also use Eclipse IDE and import this file as gradle project and run Gradle Tasks build -> assemble. (Gradle Buildship in needed in Eclipse IDE)
3. Head to directory `frontend/bin/bash` for Linux/Unix OS or `frontend/bin/win` for Window OS. Using `absc -h` to view all the available operation for ABS Compiler.
4. For all feature model analysis operation it is available in another subcommand `checkspl`. Use `absc checkspl -h` to view all available feature model analysis operation.
5. The feature model operation I had implemented is `--isvoid`,`--core`, `--variant` and `--validpconfig`  
   For void,core and variant it will only required an feature model(mtvl file) for the analysis to work.  
   For validpconfig, it will need a feature model(mtvl file) and feature selection as configuration to be validate by analysis. 
   You can choose to only input selected features **ONLY** or selected features and removed features. Example,  
   * Selected features **ONLY**: `absc checkspl --validpconfig=English,UK FeatureModelFilePath`   
    English and UK is the selected feature
   * Selected and Removed feature: `absc checkspl --validpconfig="<English,UK>,<Dutch,French>" FeatureModelFilePath`   
  The <> bracket is to seperate selected and removed feature. The program treat the first bracket as selected feature and second bracket as removed feature.

Evaluation
----------
This project I had perform unit testing using JUnit Testing. The test script is in `frontend/src/test/java/org/abs_models/frontend/mtvl/FMAnalysisTest.java`

<br/>

**Below is the default readme content for ABS Compiler/Tool Suite**
----------------------------------------------------

Inside this repository we develop the core tools of the ABS modelling
language.  The current language manual is at <https://abs-models.org/manual/>.

To compile the command-line compiler and manual, run `./gradlew assemble` (See <https://abs-models.org/getting_started/local-install/> for more information).

To run the ABS collaboratory (a browser-based IDE for ABS) locally using
Docker, execute the following command:

    docker run -p 8080:80 --rm abslang/collaboratory:latest

Then connect your browser to <http://localhost:8080/>.  It is not necessary to
clone the repository or compile the toolchain to run the ABS collaboratory in this way.

To run the absc compiler locally using docker, create a script such as
<https://github.com/abstools/abstools/blob/master/frontend/src/main/resources/bash/absc-docker>
and put it in your path.

Folders
-------

* `frontend` - the ABS compiler and runtime support.  See
  <https://abs-models.org/getting_started/local-install/> for installation
  instructions.

* `abs-docs` - the ABS language manual, available online at
  <http://abs-models.org/manual/>.  To generate the manual locally,
  run `make manual`.

  * `abs-docs/ReferenceManual` - an older LaTeX ABS reference manual,
    now mostly of historical interest

  * `abs-docs/Ott` - a formal grammar for a large subset of ABS,
    written in [Ott](https://www.cl.cam.ac.uk/~pes20/ott/)


* `org.abs-models.releng` - Files used by Jenkins and Buckminster for
  continuous integration at <https://envisage.ifi.uio.no:8080/jenkins/>.

* `abs-packages` - demonstration and description of how to use ABS
  packages (with Maven dependencies management)

* `abs-unit` - demonstration, description and initial ideas about the
  ABSUnit (a unit testing framework for ABS) (with Maven dependencies
  management)

* various leftovers from previous projects, to be evaluated and
  reactivated or pruned

Note for Windows Users
----------------------

Please clone the archive without line ending conversion (unfortunately
activated by default on Windows).  Use `-c core.autocrlf=false` as argument
for the initial `git clone` command, i.e.,

    git clone https://github.com/abstools/abstools -c core.autocrlf=false

Otherwise, running the tools inside Docker will fail with obscure error
messages.

Working with the repository
---------------------------

Consider rebasing instead of merging your changes:

    git pull --rebase

This avoids spurious "merge branch to master" commits.

`git pull --rebase` will, in case both you and the remote repository
have new commits, replay your local commits on top of upstream changes
instead of adding a new local commit that merges the `master` and
`origin/master` branches.  Conflicts have to be resolved per patch
(via `git add` + `git rebase --continue`) instead of in one go, but we
get a cleaner history.
