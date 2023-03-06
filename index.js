const report = require("multiple-cucumber-html-reporter");

report.generate({
  jsonDir: "reports",
  reportPath: "reports",

  openReportInBrowser: false,
  displayReportTime: true,
  displayDuration: true,
  saveCollectedJSON :false,
  reportName:" Report Test Node",
  pageFooter:"<center>By Javier Duarte</center>", 
  hideMetadata: true,
  
  customData: {
    title: "Run info",
    data: [
      { label: "Project", value: "Test Node" },
      { label: "Release", value: "1.0" },
    ],
  },
});