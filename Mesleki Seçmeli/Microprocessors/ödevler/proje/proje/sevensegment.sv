module sevensegment( clk, datain, grounds,display, switch_mode, pressed, incr, rot);
output logic [3:0] grounds;
output logic [6:0] display;
input logic [15:0] datain;
input logic clk; 
input logic switch_mode;
input logic pressed;
input logic incr;
input logic rot;

logic [3:0] data [3:0] ; //number to be printed on display
logic [1:0] count;       //which data byte to display.
logic [25:0] clk1;

logic has_inc;
logic has_rot;

always_ff @(posedge clk1[15]) //25 slow //19 wavy //15 perfect
begin
    grounds<={grounds[2:0],grounds[3]};
    count<=count+1;
end

always_ff @(posedge clk)
    clk1<=clk1+1;
always_comb
    case(data[count])
        0:display=7'b1111110; //starts with a, ends with g
        1:display=7'b0110000;
        2:display=7'b1101101;
        3:display=7'b1111001;
        4:display=7'b0110011;
        5:display=7'b1011011;
        6:display=7'b1011111;
        7:display=7'b1110000;
        8:display=7'b1111111;
        9:display=7'b1111011;
        'ha:display=7'b1110111;
        'hb:display=7'b0011111;
        'hc:display=7'b1001110;
        'hd:display=7'b0111101;
        'he:display=7'b1001111;
        'hf:display=7'b1000111;
        default display=7'b1111111;
    endcase
  always_ff @(posedge clk)
   begin
	 if(incr == 1 && has_inc == 0)
			begin
				if(data[0] != 4'b1111)data[0] <= data[0] +1;
				else
				begin
					data[0] <= data[0] +1;
					if(data[1] != 4'b1111)data[1] <= data[1] +1;
					else
					begin
					data[1] <= data[1] +1;
						if(data[2] != 4'b1111)data[2] <= data[2] +1;
						else
						begin
						data[2] <= data[2] +1;
						if(data[3] != 4'b1111)data[3] <= data[3] +1;
						else
						begin
						data[3] <= data[3]+1;
						end
						end
					end
				end
				has_inc = 1;
			end
	
	if(incr == 0 && has_inc == 1) has_inc = 0;
	
	if(rot == 1 && has_rot == 0)
	begin
				data[3] <= data[2];
				data[2] <= data[1];
				data[1] <= data[0];
				data[0] <= data[3];
				has_rot =1;
	end
	
	if(rot == 0 && has_rot == 1) has_rot = 0;
	
	
	else if(pressed == 1)
		begin
			
			if(switch_mode == 0)
				begin
				data[3] <= datain[3:0];
				data[2] <= data[3];
				data[1] <= data[2];
				data[0] <= data[1];
				end
			else
			begin
				data[3] <= data[2];
				data[2] <= data[1];
				data[1] <= data[0];
				data[0] <= datain[3:0];
			end
		end
		end
initial begin
    count = 2'b0;
    grounds=4'b1110;
    clk1=0;
	 has_inc =1;
	 has_rot = 1;
end


endmodule