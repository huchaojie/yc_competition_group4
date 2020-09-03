const data = {
      labels: ["2 Feb", "3 Feb", "4 Feb", "5 Feb*", "6 Feb", "7 Feb", "8 Feb", "9 Feb*", "10 Feb*", "11 Feb", "12 Feb", "13 Feb", "14 Feb*", "15 Feb", "16 Feb", "17 Feb*", "18 Feb*", "19 Feb"],
      datasets: [
        {
          name: "隔离", chartType: 'line',
          values: [34, 35, 33, 33, 33, 30, 29, 29, 29, 26, 10, 10, 10, 10, 10, 10, 10, 10]
        }
      ],
      yRegions: [
        { label: "人数",
          start: 0, end: 40,
          options: { labelPos: 'right' }
        }
      ]
}

const chart = new frappe.Chart( "#chart", {
    data: data,
    title: "隔离图（*数值展示）",
    type: 'line',
    height: 500,
    colors: ['purple', '#ffa3ef']
});