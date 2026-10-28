module vga_sync
  (input logic        clk,
   output logic       hsync,
   output logic       vsync,
   output logic [2:0] rgb,
	input [15:0] x,
	input [15:0] y);

   logic pixel_tick, video_on;
   logic [9:0] h_count;
   logic [9:0] v_count;

   localparam HD       = 640, //horizontal display area
              HF       = 48,  //horizontal front porch
              HB       = 16,  //horizontal back porch
              HFB      = 96,  //horizontal flyback
              VD       = 480, //vertical display area
              VT       = 10,  //vertical top porch
              VB       = 33,  //vertical bottom porch
              VFB      = 2,   //vertical flyback
                  LINE_END = HF+HD+HB+HFB-1,
              PAGE_END = VT+VD+VB+VFB-1;

   always_ff @(posedge clk)
     pixel_tick <= ~pixel_tick; //25 MHZ signal is generated.

   always_ff @(posedge clk)
     if (pixel_tick) begin
        if (h_count == LINE_END)
          begin
              h_count <= 0;
                  if (v_count == PAGE_END)
                        v_count <= 0;
                  else
                     v_count <= v_count + 1;
                     end
        else
          h_count <= h_count + 1;
     end
      
    //color generation  
   always_comb
        begin
            
             if((h_count < HD) && (v_count < VD))// if video on
				 begin
               rgb = 3'b010;
					if((h_count > x-6) && (h_count < x+6) && (v_count > y-6) && (v_count < y+6))
						begin
						rgb = 3'b100;
						end
				 end
				 else
					rgb = 3'b0;
        end
    //output signals
   assign hsync = (h_count >= (HD+HB) && h_count <= (HFB+HD+HB-1));
   assign vsync = (v_count >= (VD+VB) && v_count <= (VD+VB+VFB-1));

   initial
     begin
        h_count = 0;
        v_count = 0;
        pixel_tick = 0;
     end

endmodule