'use strict';

/* Controllers */

angular.module('app')
  .controller('AppCtrl', ['$scope',  '$localStorage', '$window', 'Auth',
    function(              $scope,     $localStorage,   $window ,Auth) {
      // add 'ie' classes to html
      var isIE = !!navigator.userAgent.match(/MSIE/i);
      if(isIE){ angular.element($window.document.body).addClass('ie');}
      if(isSmartDevice( $window ) ){ angular.element($window.document.body).addClass('smart')};

      // config
      $scope.app = {
        name: 'LUX FACTORY',
        version: '2.0.0',
        // for chart colors
        color: {
          primary: '#7266ba',
          info:    '#23b7e5',
          success: '#27c24c',
          warning: '#fad733',
          danger:  '#f05050',
          light:   '#e8eff0',
          dark:    '#3a3f51',
          black:   '#1c2b36'
        },
        settings: {
          themeID: 3,
          navbarHeaderColor: 'bg-black',
          navbarCollapseColor: 'bg-white-only',
          asideColor: 'bg-black',
          headerFixed: true,
          asideFixed: false,
          asideFolded: false,
          asideDock: false,
          container: false
        }
      }

      // save settings to local storage
      if ( angular.isDefined($localStorage.settings) ) {
        $scope.app.settings = $localStorage.settings;
      } else {
        $localStorage.settings = $scope.app.settings;
      }
      $scope.$watch('app.settings', function(){
        if( $scope.app.settings.asideDock  &&  $scope.app.settings.asideFixed ){
          // aside dock and fixed must set the header fixed.
          $scope.app.settings.headerFixed = true;
        }
        // for box layout, add background image
        $scope.app.settings.container ? angular.element('html').addClass('bg') : angular.element('html').removeClass('bg');
        // save to local storage
        $localStorage.settings = $scope.app.settings;
      }, true);

      // angular translate
      

      function isSmartDevice( $window )
      {
          // Adapted from http://www.detectmobilebrowsers.com
          var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
          // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
          return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
      }

      
      $scope.dtOptions = {
        paginationType : 'full_numbers',
            displayLength: 15,
            bFilter:false,
            bLengthChange: false,
            bInfo: false
            ,
            oLanguage:{
              "sEmptyTable":     "Aucun enregistrement disponible",
              "oPaginate": {
                  "sFirst":      "<<",
                  "sPrevious":   "<",
                  "sNext":       ">",
                  "sLast":       ">>"
               }
               // sUrl:'lang/dataTables_fr.txt'
            }
      };
           
        $scope.dtColumnDefs = {
          aoColumnDefs: [
            {
                aTargets: 0,
                bSortable: false
            }
        ]
        };
      
  }]);
