module main
  (input logic        i_clk,
   output logic [3:0] o_grounds,
   output logic [6:0] o_display
	input logic ps2d,
	input logic ps2c);
   logic [15:0] data;
	
	

	
   sevensegment sevensegment_0
     (.clk(i_clk),
      .datain(data),
      .grounds(o_grounds),
      .display(o_display));
		
		keyboard keyboard_0(.ps2d(ps2d), .ps2c(ps2c))
		
		
   
	  
   initial
     begin
        data = 4'hAAAA;
     end
endmodule