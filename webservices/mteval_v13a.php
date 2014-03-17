<?php

/**
 * @file algowsdl.php
 * 
 * @author  Yingjie Li <yil308@lehigh.edu>
 * @author  Bart Lamiroy <lamiroy@cse.lehigh.edu>
 * 
 * @date October 2010
 * 
 * @version 1.0
 *
 * @section Main Description
 * 
 * This file is the principal stub for WSDL based Web Service providing.
 * It should be taken as a template for adaptation and extension of local
 * execution of DAE related services.
 * 
 * @section Usage - Disclaimer
 * 
 * The only part of this code that needs to be modified is the \a callback()
 * function. All other parts of this code should be left untouched. Relying on
 * personalized modification of the rest of the code may result in loss of 
 * compatibility with subsequent updates and evolutions of this code.
 *   
 * @section LICENSE
 * 
 *  This file is part of the DAE Platform Project (DAE).
 *
 *  DAE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  DAE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with DAE.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @mainpage
 * 
 * @section Contents
 * 
 * This project is composed of 3 files:
 *  - ocrad.php
 *  - client.php
 *  - setup.php
 * 
 * @section Note
 * 
 * For demo executions, and if the \a $algoname variable is modified in setup.php, this file
 * should be renamed to \a $algoname.php.
 */
 
//== START EDITABLE ZONE 

/**
 * \brief Name of hosted algorithm.
 *  
 * Should exactly match SQL query "select NAME from ALGORITHM where ID = \a $algoOracleID"
 * 
 * @var $algoname 
 */
$algoname = 'mteval';
$algoversion = 'v13a';

/**
 * Edit the contents of setup.php to fit your needs.
 */ 
include('setup.php');

/**
 * Array of input variables for hosted algorithm.
 * 
 * \todo write an extensive explanation on how to format this part
 * 
 * IMPORTANT: due to SOAP restrictions, the 'name' string cannot contain whitespaces. 
 * This is an important constraint, given the assumed correspondence with the Oracle
 * data or data_type mapping.
 * 
 * @var $inputT
 */
$inputT = array();
$inputT['mteval_source_file'] = array('name'=>'mteval_source_file', 'type'=>'xsd:string');
$inputT['mteval_reference_file'] = array('name'=>'mteval_reference_file', 'type'=>'xsd:string');
$inputT['mteval_hypothesis_file'] = array('name'=>'mteval_hypothesis_file', 'type'=>'xsd:string');


/**
 * Array of output variables for hosted algorithm.
 * 
 * \todo write an extensive explanation on how to format this part
 * 
 * IMPORTANT: due to SOAP restrictions, the 'name' string cannot contain whitespaces. 
 * This is an important constraint, given the assumed correspondence with the Oracle
 * data or data_type mapping.
 * 
 * @var $outputT
 */
$outputT['mteval_output'] = array('name'=>'mteval_output', 'type'=>'xsd:string');
//== STOP EDITABLE ZONE

/**
 * This function is currently a demo stub running 'Ocrad'. Eventually this will launch the 
 * effective execution of the corresponding algorithm (as set in the \a $input URIs array parameter)
 *  
 * @param [in] $input the array of arguments related to the execution
 * 
 * The exact input and output parameters of this function are defined by the \a $inputT and \a $outputT variables in \a setup.php.
 * 
 */
function callback($input) {

	global $htmlBaseDir;
	global $executionDir;
	global $executionPrefix;
	global $algoname;
    global $hostname;
	
	// Creating temporary work directory
	$localdir = $htmlBaseDir . '/' . $executionDir . '/' . $algoname . '/' . time();
	if (!is_dir($localdir) && ! mkdir($localdir,0777,true)) {
	    return new soap_fault('SERVER', '', 'Cannot create local directory', '');
	}
	chmod($localdir,0777);
	
	//== START EDITABLE ZONE
	/*
	 * The following editable zone is made for retrieving the input parameters
	 * and preparing them (copying on disk, other preparatory manipulations) to
	 * be provided as input arguments to the actual executable.
	 * 
	 * In this case there is only one input parameter: the input file
	 * 
	 * ATTENTION! When modifying this code, be sure the array keys correspond to 
	 * the keys declared in \a $inputT in setup.php
     */
    	$inputSource = $input['mteval_source_file'];
	$inputReference = $input['mteval_reference_file'];
	$inputHypothesis = $input['mteval_hypothesis_file'];
	
	/*
	 * This is code is only doing raw execution without the required logging
	 * on the DAE server. Logging provenance on the DAE server can only
	 * be done when the software and service are dully registered with
	 * the platform.
	 */ 
	

    //Getting source file  
    
	
	$sourceFile = $localdir.'/'.array_pop(explode("/",$inputSource));
	if (!copy($inputSource,$sourceFile)) {
            error_log('Cannot copy file '.$inputHypothesis);
	    return new soap_fault('SERVER', '', 'Execution Error', 'Cannot copy file');
	}	

    //Getting reference file  
   

	$referenceFile = $localdir.'/'.array_pop(explode("/",$inputReference));
	if (!copy($inputReference,$referenceFile)) {
            error_log('Cannot copy file '.$inputReference);
	    return new soap_fault('SERVER', '', 'Execution Error', 'Cannot copy file');
	}	
 
    //Getting hypothesis file

	$hypothesisFile = $localdir.'/'.array_pop(explode("/",$inputHypothesis));
	if (!copy($inputHypothesis,$hypothesisFile)) {
            error_log('Cannot copy file '.$inputHypothesis);
	    return new soap_fault('SERVER', '', 'Execution Error', 'Cannot copy file');
	}	

    //Running mteval

    $execString = 'perl /home/dae/WebServices/mteval-v13a.pl -r '.$referenceFile.' -s '.$sourceFile.' -t '.$hypothesisFile.' -c -d 2 -b > '.$localdir.'/output.txt';
    //== STOP EDITABLE ZONE
	
	$returnValue = system($execString,$returnCode);
	if ($returnCode) {
	    return new soap_fault('SERVER', $error, 'Execution Error', 'Return code = ' . $returnCode .' '. $execString);
	}

	//== START EDITABLE ZONE
	/*
	 * This editable zone retrieves the results from the execution and creates the appropriate
	 * output data structure for retrieval by the caller.
	 * 
	 * In this particular case, the execution output consists of 2 files that have been stored
	 * in the execution directory. Since this directory is accessible by the webserver, we construct
	 * the appropriate URLs so that the client can download them.
	 */
	// Stripping absolute path information and prefixing URL information.
	//$localOCR = 'http://'.$hostname. substr($localOCR,strlen($htmlBaseDir));
	//$localLayout = 'http://'.$hostname. substr($localLayout,strlen($htmlBaseDir));
		
	// ATTENTION! Be sure the array keys correspond to the keys declared in \a $outputT in setup.php
	/*$result = array(
   	'ocr_result_file' => $localOCR, 
       	'layout_result_file' => $localLayout
    );*/
    $result=array('mteval_output' => 'http://localhost/'.substr($localdir,9).'/output.txt');
    //== STOP EDITABLE ZONE
    
    return $result;
}

/*
 * This PHP page genereates WSDL files for different algorithms with different inputs and outputs.
 */
 
// Pull in the NuSOAP code
require_once('lib/nusoap.php');

/**
 * Publishes a WSDL service for the algorithm defined in \a setup.php. 
 * It's WSDL interface description is returned. If not, it is supposed to be combined 
 * with an HTTP_POST upload of the required input data.
 * 
 * \todo UDDI registration on the DAE server.
 * 
 * THIS CODE SHOULD NOT BE MODIFIED WHEN ADAPTED TO SOME LOCAL SERVICE. 
 * ALL CODE MODIFICATION, CONFIGURATION AND ADAPTATION SHOULD BE DONE IN EITHER
 * \a setup.php OR IN THE ABOVE \a callback() FUNCTION.
 * 
 * Modification of the code below may result in incompatibility issues or difficulties
 * to upgrade to newer versions.
 */
 
// Create the server instance
$server = new soap_server();

// Retrieve the path of the algorithm to be invoked
//$algopath = $db->r("select PATH from ALGORITHM where ID = ".$algoid);
	
// Initialize WSDL support
$server->configureWSDL($algoname.'wsdl', 'urn:'.$algoname.'wsdl');

/*
 * Generate an array of input parameters as resulting from the following query
 * "select NAME from DATATYPE where ID in (	select DATATYPE_ID from TYPE_OF where DATA_ID in (
 *				select DATA_ID from ALGORITHM_INPUT where ALGORITHM_ID = ". $algoid ."	))"
 *
 * This array (\a $inputT) is defined in \a setup.php
 *  
 */ 
$server->wsdl->addComplexType('inputType_'.$algoname, 'complexType',	'struct',
							  'all', '', $inputT);

/*
 * Generate an array of output parameters as resulting from the following query
 * "select NAME from DATATYPE where ID in (	select DATATYPE_ID from TYPE_OF where DATA_ID in (
 *				select DATA_ID from ALGORITHM_OUTPUT where ALGORITHM_ID = ". $algoid ."	))" 
 *
 * This array (\a $outputT) is defined in \a setup.php
 *  
 */ 
$server->wsdl->addComplexType('outputType_'.$algoname, 'complexType', 'struct', 
							  'all', '', $outputT);

// Register the method to expose
$server->register('callback',                	// method PhP callback function
		array('args' => 'tns:inputType_'.$algoname),  	//input parameters
		array('return' => 'tns:outputType_'.$algoname), // output parameters
		'urn:'.$algoname.'wsdl',				// namespace              
		'urn:'.$algoname.'wsdl#'.$algoname,		// soapaction
   		'rpc',                                	// style
   		'encoded',                            	// use          
		$algoname.' algorithm'					// documentation
);


// Use the POST request to (try to) invoke the service
$HTTP_RAW_POST_DATA = isset($HTTP_RAW_POST_DATA) ? $HTTP_RAW_POST_DATA : '';
$server->service($HTTP_RAW_POST_DATA); 
?>
