/**
*
* to enable the loading of the extension delete all keys ExtensionInstallBlacklist in registry
*
*
*
*/



var basePath = __dirname +"/../" ;
console.log("testing protractor script from "  + basePath);

// the jqueryhighlighter module that manage the exetension 
var {Jqh}= require('./node_modules/jqueryhiglighter/jqh.js');



var HtmlScreenshotReporter = require('protractor-jasmine2-screenshot-reporter');

var reporter = new HtmlScreenshotReporter({
  dest: 'target/screenshots',
  filename: 'my-report.html'
});



var proxy='pslux-vip-user.snmc.cec.eu.int:8012'
exports.config = {
  framework: 'jasmine',
  seleniumAddress: 'http://localhost:4444/wd/hub',
  

  specs: ['spec.js'],

  SELENIUM_PROMISE_MANAGER: false,

  capabilities: {
    'browserName': 'chrome',
    'chromeOptions': {
   'useAutomationExtension': false,
'args': ['--disable-dev-shm-usage','--no-sandbox' ],
        'extensions': [Jqh.getExtensionData()]
    },
'proxy': {
'proxyType': 'manual',
'httpProxy': proxy,
'sslProxy':proxy
}
  }		
,
afterLaunch: function(exitCode) {
  return new Promise(function(resolve){
    reporter.afterLaunch(resolve.bind(this, exitCode));
  });
},
beforeLaunch: function() {
  return new Promise(function(resolve){
    reporter.beforeLaunch(resolve);
  });
},
          
    onPrepare: function(){
      browser.waitForAngularEnabled(false);

//console.log(" browser.waitForAngularEnabled is set to false as for a non angular application " );


jasmine.getEnv().addReporter(reporter);
	
  }

}