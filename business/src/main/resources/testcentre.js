/***
**
**    testcentre implementation
**
**
*/

var TC = {


	testObject : function(identMethod,ident,argument) {
			this.identMethod=identMethod;
			this.ident=ident;
			this.argument=argument;
	},
	
	
	click:function( testobject ){
		
		console.log("click not implemented");
		
	}
	
	wait : function() {
		
	console.log("wait not implemented");	
		
	},
	
	waitOn : function (testobject) {
	
console.log("wait on not implemented");	
		
	},	
	
	set : function(testobject , arg ){
		
	console.log("set not implemented");
		
		
	},
	
	verify : function(testobject ,arg ){
		
	console.log("set not implemented");
		
		
	},
	
	navigate : function(arg ){
		
	console.log("set not implemented");
		
		
	},          

	evaljsOnObect : function(testobject, arg ){
		
	console.log("set not implemented");
		
		
	}

};


exports.TC=TC;


