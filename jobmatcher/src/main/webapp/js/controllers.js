var MainCtrl = function ($scope,EventBroadcast,$timeout,$route,$location,$routeParams) {
     $scope.globalAlert = { show: false, type: 'success', msg: 'ALERT'} ;

     $scope.hasError = $location.$$path == "/error";
    $scope.closeGlobalAlert = function () {
            $scope.globalAlert.show = false;
    }


    $scope.$on('showGlobalAlert', function(event,alert) {

        $scope.globalAlert.type = alert.type;
        $scope.globalAlert.show = true;
        $scope.globalAlert.msg = alert.msg;
        $timeout(function() {
            $scope.$apply(function () {
                $scope.globalAlert.show = false;
            });
        }, 3000);

    });


}

var JobSeekerRegistrationCtrl = function ($scope,JobSeeker,EventBroadcast) {
    $scope.alertError = {type : 'error', msg: '', show: false };
    $scope.newJobSeeker = {};

    $scope.jobseekers = JobSeeker.query({},function() {
          var jobseekers = $scope.jobseekers;

    });


    $scope.jobseeker = JobSeeker.get({id : '25199794331375264773326513520'},
        function() {
                 var jobseeker = $scope.jobseeker;
                 var name = jobseeker.name;

        }
    );
    $scope.open = function () {
        $scope.dialogOpen = true;
        $scope.newJobSeeker = {};
        $scope.alertError = {type : 'error', msg: '', show: false };
    };

    $scope.close = function () {
        $scope.dialogOpen = false;
    };


    $scope.submit = function() {
         try {
             $scope.validate();
             JobSeeker.save($scope.newJobSeeker,function(arg){
                 $scope.close();
                 EventBroadcast.broadcast('showGlobalAlert',{type: 'success', msg: 'Success: JobSeeker account created'});
             })
         } catch(error) {
               $scope.alertError.msg = "Error: " + error;
               $scope.alertError.show = true;

         }




    }



    $scope.validate = function() {
        if($scope.newJobSeeker.email == null || $scope.newJobSeeker.email.length == 0 ) {
           throw "Email field is required";
        }

        if($scope.newJobSeeker.password == null || $scope.newJobSeeker.password.length == 0 ) {
            throw "Password field is required";
        }

        if($scope.newJobSeeker.confirm_password == null || $scope.newJobSeeker.confirm_password.length == 0 ) {
            throw "Confirm password is required";
        }

        if($scope.newJobSeeker.states == null || $scope.newJobSeeker.states.length == 0 ) {
            throw "State field is required";
        }

        if($scope.newJobSeeker.zip == null || $scope.newJobSeeker.zip.length == 0 ) {
            throw "Zip field is required";
        }

        if($scope.newJobSeeker.password != $scope.newJobSeeker.confirm_password) {
            throw "Password and Confirm Password field must match";
        }








    }




    $scope.hideError = function () {
        $scope.alertError.show = false;
    };

    $scope.opts = {
        backdropFade: true,
        dialogFade:true
    };



};




var HiringManagerRegistrationCtrl = function ($scope,HiringManager,EventBroadcast) {
    $scope.alertError = {type : 'error', msg: '', show: false };
    $scope.newHiringManager = {address: [{}]};





    $scope.open = function () {
        $scope.dialogOpen = true;
        $scope.newHiringManager = {address: [{}]};
        $scope.alertError = {type : 'error', msg: '', show: false };


    };

    $scope.close = function () {
        $scope.dialogOpen = false;

    };


    $scope.submit = function() {
        try {
            $scope.validate();
            HiringManager.save($scope.newHiringManager,function(arg){
                $scope.close();
                EventBroadcast.broadcast('showGlobalAlert',{type: 'success', msg: 'Success: HiringManager account created'});
            })
        } catch(error) {
            $scope.alertError.msg = "Error: " + error;
            $scope.alertError.show = true;

        }




    }



    $scope.validate = function() {
        if($scope.newHiringManager.email == null || $scope.newHiringManager.email.length == 0 ) {
            throw "Email field is required";
        }

        if($scope.newHiringManager.password == null || $scope.newHiringManager.password.length == 0 ) {
            throw "Password field is required";
        }

        if($scope.newHiringManager.confirm_password == null || $scope.newHiringManager.confirm_password.length == 0 ) {
            throw "Confirm password is required";
        }
        if($scope.newHiringManager.company_name == null || $scope.newHiringManager.company_name.length == 0 ) {
            throw "Company Name is required";
        }

        if($scope.newHiringManager.address[0].states == null || $scope.newHiringManager.address[0].states.length == 0 ) {
            throw "State field is required";
        }

        if($scope.newHiringManager.address[0].zip == null || $scope.newHiringManager.address[0].zip.length == 0 ) {
            throw "Zip field is required";
        }

        if($scope.newHiringManager.password != $scope.newHiringManager.confirm_password) {
            throw "Password and Confirm Password field must match";
        }








    }




    $scope.hideError = function () {
        $scope.alertError.show = false;
    };

    $scope.opts = {
        backdropFade: true,
        dialogFade:true
    };



};




var PostNewJobCtrl = function ($scope,Job,EventBroadcast) {
    $scope.alertError = {type : 'error', msg: '', show: false };
    $scope.newJob = {
        jobTitle: "Software Engineer",
        jobDescription:  "Blah Blah",
        desiredSkills: "Java",
        experienceLevel: "Entry_Level",
        companyName: "Company",
        address: [{address: "Address", city: "City", states: "California", zip: "94560" }]




    };





    $scope.open = function () {
        $scope.dialogOpen = true;
        $scope.newJob = {address: [{}]};
        $scope.alertError = {type : 'error', msg: '', show: false };


    };

    $scope.close = function () {
        $scope.dialogOpen = false;

    };


    $scope.submit = function() {
        try {
            $scope.validate();
            Job.save($scope.newJob,function(arg){
                $scope.close();
                EventBroadcast.broadcast('showGlobalAlert',{type: 'success', msg: 'Success: Job created'});
            });
            EventBroadcast.broadcast('jobsRefresh',{});
        } catch(error) {
            $scope.alertError.msg = "Error: " + error;
            $scope.alertError.show = true;

        }




    }



    $scope.validate = function() {
        if($scope.newJob.jobTitle == null || $scope.newJob.jobTitle.length == 0 ) {
            throw "Job Title required";
        }

        if($scope.newJob.jobDescription == null || $scope.newJob.jobDescription.length == 0 ) {
            throw "Job Description is required";
        }


        if($scope.newJob.desiredSkills == null ||  $scope.newJob.desiredSkills.length == 0) {
            throw "Skills is required";
        }

        if($scope.newJob.experienceLevel != $scope.newJob.experienceLevel) {
            throw "Experience Level is required";
        }



        if($scope.newJob.companyName == null || $scope.newJob.companyName.length == 0 ) {
            throw "Company Name is required";
        }

        if($scope.newJob.address[0].states == null || $scope.newJob.address[0].states.length == 0 ) {
            throw "State field is required";
        }

        if($scope.newJob.address[0].zip == null || $scope.newJob.address[0].zip.length == 0 ) {
            throw "Zip field is required";
        }









    }




    $scope.hideError = function () {
        $scope.alertError.show = false;
    };

    $scope.opts = {
        backdropFade: true,
        dialogFade:true
    };
    
    
    



};

var JobCtrl = function ($scope,Job,EventBroadcast,$timeout) {
    $scope.alertError = {type : 'error', msg: '', show: false };
    $scope.myData = Job.query();

    $scope.gridOptions = { data: 'myData',
        columnDefs: [
            {field: 'jobTitle', displayName: 'Job Title'},
            {field: 'jobDescription', displayName: 'Description'},
            {field: 'desiredSkills', displayName: 'Skills'},

            {field: 'jobPostedDate', displayName: 'Posted Date',
            cellTemplate: "<div class=\"ngCellText colt{{$index}}\">{{row.getProperty(col.field) | date:'longDate'}}</div>"

            },
            {field: 'companyName', displayName: 'Company Name'},
            {field: 'address[0].city', displayName: 'Location'}
        ]


    };

    $scope.$on('jobsRefresh', function(event,alert) {
       $timeout(function() {
           $scope.myData = Job.query();

       },2000)  ;



    });







};


var JobSearchCtrl = function ($scope,JobSearch,EventBroadcast,$timeout) {
    $scope.alertError = {type : 'error', msg: '', show: false };

    $scope.gridOptions = { data: 'myData',
        columnDefs: [
            {field: 'jobTitle', displayName: 'Job Title'},

            {field: 'jobDescription', displayName: 'Description'},
            {field: 'desiredSkills', displayName: 'Skills'},
            {field: 'jobPostedDate', displayName: 'Posted Date',
                cellTemplate: "<div class=\"ngCellText colt{{$index}}\">{{row.getProperty(col.field) | date:'longDate'}}</div>"

            },
            {field: 'companyName', displayName: 'Company Name'},
            {field: 'address[0].city', displayName: 'Location'}
        ]


    };

    $scope.search = function() {
        $scope.myData = JobSearch.query({keyword: $scope.keyword});
    }









};

