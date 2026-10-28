module main_module ( input clk, input ps2c, input ps2d, output logic [3:0] grounds, output logic
[6:0] display, input pushbutton, output hsync, output vsync, output [2:0] rgb);
logic [15:0] data_all;
logic [15:0] keyboard_data;
logic [3:0] keyout;
logic [15:0] x;
logic [15:0] y;
logic ack;

//memory map is defined here
localparam 
BEGINMEM=12'h000,
ENDMEM=12'h1ff,
KEYBOARD=12'h900,
SEVENSEG=12'hb00,
VGA_X=12'ha00,
VGA_Y=12'ha01;
// memory chip
logic [15:0] memory [0:127];	// ram.dat'in satir sayisina dikkat
// cpu's input-output pins
logic [15:0] data_out;
logic [15:0] data_in;
logic [11:0] address;
logic memwt;

sevensegment ss1 (.din(data_all), .grounds(grounds), .display(display), .clk(clk));
keyboard kb1(.clk(clk), .ps2d(ps2d), .ps2c(ps2c), .ack(ack), .dout(keyboard_data));
bird br1(.clk(clk), .data_in(data_in),.data_out(data_out), .address(address),.memwt(memwt));
vga_sync(.clk(clk), .vsync(vsync), .hsync(hsync),.rgb(rgb), .x(x), .y(y));

//multiplexer for cpu input
always_comb
begin
	if ( (BEGINMEM<=address) && (address<=ENDMEM) )
	begin
		data_in=memory[address];
		ack = 0;
	end
	else if (address==KEYBOARD+1)
	begin
		data_in = keyboard_data;
		ack = 0;
	end
	else if (address==KEYBOARD)
	begin
		data_in = keyboard_data;
		ack = 1;
	end
	else begin
		data_in=16'hf345; //any number
		ack = 0;
	end
end

//multiplexer for cpu output
always_ff @(posedge clk) //data output port of the cpu
begin
	if (memwt)
	begin
		if ( (BEGINMEM<=address) && (address<=ENDMEM) )
			memory[address]<=data_out;
		else if ( SEVENSEG==address)
			data_all<=data_out;
		else if(VGA_X == address) x <= data_out;
		
		else if(VGA_Y == address) y <= data_out;
	end
	
end

initial
begin
	$readmemh("ram.dat", memory);
end
endmodule