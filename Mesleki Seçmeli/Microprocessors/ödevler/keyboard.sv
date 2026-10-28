module keyboard(
                        input logic clk,
                        input logic ps2d,
                        input logic ps2c,
                                input logic ack,
                        output logic [15:0] dout
                                );
localparam [1:0] 
                IDLE = 2'b00,
                READ = 2'b01,
                END =  2'b10;
                
logic [7:0] filter;
logic rx_done_tick;
logic [3:0] count;
logic [1:0] state;
logic [1:0] c;
logic fall_edge;
logic [10:0] char;
logic status;

//filter falling edge ps2c
always_ff @(posedge clk)
begin
    filter <= {ps2c,filter[7:1]};
    if (filter == 8'b11111111)
        c<= {1'b1,c[1]};           //c[0] is past, c[1] is now.
    else if (filter == 8'b00000000)
        c<={1'b0,c[1]};
end
 
 assign fall_edge = c[0] & ~c[1];  //falling edge..
 
 //FSM
 always_ff @ (posedge clk)
 begin
   rx_done_tick <= 1'b0;
   case (state)
    IDLE:
        if(fall_edge & ~status)
            begin
                char <= {ps2d,char[10:1]};
                count <= 4'd9;
                state <= READ;
            end
            
    READ:
        if(fall_edge)
            begin
                char <= {ps2d,char[10:1]};
                if(count == 0)
                    state <= END;
                else
                    count <= count -1;
            end
            
    END:
        begin
          rx_done_tick <= 1'b1;
        state <= IDLE;
        end
 endcase
 end
 
always_ff @ (posedge clk)
    if(rx_done_tick)
        status<=1'b1;
   else if (status ==1 & ack == 1)
       status<=1'b0;

 always_comb
  if (ack ==1'b1)
     dout = 16'(char[8:1]);// assign scan code
  else
      dout = 16'(status);
      
 
 initial
 begin
    status =0;
    state = IDLE;
 end
 
 endmodule