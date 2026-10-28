module top
  (input logic        i_clk,
   input logic [3:0]  i_keypad4x4_col_data,
	input logic switch_mode,
	input logic increment,
	input logic rotate,
   output logic [3:0] o_keypad4x4_row_data,
   output logic [3:0] o_grounds,
   output logic [6:0] o_display,
	output logic switch_power);
   logic [15:0] keypad4x4_data;
   logic [15:0] data;
   logic        rselect;
   logic        ack;
	
	logic pressed;
	

	
   sevensegment sevensegment_0
     (.clk(i_clk),
      .datain(data),
      .grounds(o_grounds),
      .display(o_display),
		.switch_mode(switch_mode),
		.pressed(pressed),
		.incr(increment),
		.rot(rotate));
   ctrl_keypad4x4 ctrl_keypad4x4_0
     (.i_clk(i_clk),
      .i_ack(ack),
      .i_rselect(rselect),
      .i_col_data(i_keypad4x4_col_data),
      .o_row_data(o_keypad4x4_row_data),
      .o_data(keypad4x4_data));
		
		
   
	always_ff @(posedge i_clk)
     begin
			if(pressed == 1) pressed <=0;
	  
        if ((keypad4x4_data[0] == 1'b1) && (rselect == 1)) begin
           ack <= 1;
           rselect <= 0;
        end else if (rselect == 0) begin
           data <= keypad4x4_data;			//inputu okuyor
			  pressed <= 1;
           ack <= 0;
           rselect <= 1;
        end else begin
           ack <= 0;
           rselect <= 1;
        end
     end
   initial
     begin
        data = 0;
        ack = 0;
        rselect = 1;
		  pressed = 0;
     end
endmodule