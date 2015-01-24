
var rABS = typeof FileReader !== "undefined" && typeof FileReader.prototype !== "undefined" && typeof FileReader.prototype.readAsBinaryString !== "undefined";

var wtf_mode = false;

function fixdata(data) {
	var o = "", l = 0, w = 10240;
	for(; l<data.byteLength/w; ++l) o+=String.fromCharCode.apply(null,new Uint8Array(data.slice(l*w,l*w+w)));
	o+=String.fromCharCode.apply(null, new Uint8Array(data.slice(l*w)));
	return o;
}

function ab2str(data) {
	var o = "", l = 0, w = 10240;
	for(; l<data.byteLength/w; ++l) o+=String.fromCharCode.apply(null,new Uint16Array(data.slice(l*w,l*w+w)));
	o+=String.fromCharCode.apply(null, new Uint16Array(data.slice(l*w)));
	return o;
}

function s2ab(s) {
	var b = new ArrayBuffer(s.length*2), v = new Uint16Array(b);
	for (var i=0; i != s.length; ++i) v[i] = s.charCodeAt(i);
	return [v, b];
}

function xlsxworker_noxfer(data, cb) {
	var worker = new Worker('./xlsxworker.js');
	worker.onmessage = function(e) {
		switch(e.data.t) {
			case 'ready': break;
			case 'e': console.error(e.data.d); break;
			case 'xlsx': cb(JSON.parse(e.data.d)); break;
		}
	};
	var arr = rABS ? data : btoa(fixdata(data));
	worker.postMessage({d:arr,b:rABS});
}
var resultJson;
function xlsxworker_xfer(data, cb) {
	var worker = new Worker(rABS ? './scripts/xlsx/xlsxworker2.js' : './scripts/xlsx/xlsxworker1.js');
	var resultText;
	worker.onmessage = function(e) {
		switch(e.data.t) {
			case 'ready': break;
			case 'e': console.error(e.data.d); break;
			default: xx=ab2str(e.data).replace(/\n/g,"\\n").replace(/\r/g,"\\r"); console.log("done"); resultText=cb(JSON.parse(xx)); break;
		}
		return resultText;
	};
	if(rABS) {
		var val = s2ab(data);
		resultText = worker.postMessage(val[1], [val[1]]);
	} else {
		resultText = worker.postMessage(data, [data]);
	}
	
	return resultText;
}

function xlsxworker(data, cb) {
	transferable = true;
	if(transferable) return xlsxworker_xfer(data, cb);
	else xlsxworker_noxfer(data, cb);
}

function to_json(workbook) {
	var result = {};
	workbook.SheetNames.forEach(function(sheetName) {
		var roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
		if(roa.length > 0){
			result[sheetName] = roa;
		}
	});
	return result;
}


var out = document.getElementById('outdiv');
function process_wb(wb){
	output = JSON.stringify(to_json(wb), 2, 2);
	resultJson = output;
	//Call back to the Java method
	window.handleJson(output);
	return output;
}


var xlf = document.getElementById('xlf');
function handleFile(e) {
	rABS = true; /*= document.getElementsByName("userabs")[0].checked;*/
	use_worker = true; /*document.getElementsByName("useworker")[0].checked;*/
	var files = e.target.files;
	var oResults;
	var i,f;
	for (i = 0, f = files[i]; i != files.length; ++i) {
		var reader = new FileReader();
		var name = f.name;
		reader.onload = function(e) {
			if(typeof console !== 'undefined') console.log("onload", new Date(), rABS, use_worker);
			var data = e.target.result;
			if(use_worker) {
				oResults = xlsxworker(data, process_wb);
				//alert(oResults)
			} else {
				var wb;
				if(rABS) {
					wb = XLSX.read(data, {type: 'binary'});
				} else {
				var arr = fixdata(data);
					wb = XLSX.read(btoa(arr), {type: 'base64'});
				}
				process_wb(wb);
			}
		};
		if(rABS) reader.readAsBinaryString(f);
		else reader.readAsArrayBuffer(f);
	}
	
}

